//Import the express and url modules
var express = require('express');
var url = require("url");

//Import the database
var db= require('./db.js');

//Status codes defined in external file
require('./http_status.js');

//The express module is a function. When it is executed it returns an app object
var app = express();
app.use(express.static("public"));

//Set up the application to handle GET requests sent to the user path
app.get('/games', handleGetRequest );
app.get('/games/*', handleGetRequest);
app.get('/games/search', handleGetRequest)


//Start the app listening on port 8080
app.listen(8080,() => console.log('Server listening on port 8080!'));


/* Handles GET requests sent to web service.
   Processes path and query string and calls appropriate functions to
   return the data. */
function handleGetRequest(request, response){
    //Parse the URL
    var urlObj = url.parse(request.url, true);

    //Extract object containing queries from URL object.
    var queries = urlObj.query;

    //Get the pagination properties if they have been set. Will be  undefined if not set.
    var numItems = queries['num_items'];
    var offset = queries['offset'];

    //Get the search property if have been set. Will be  undefined if not set.
    var query = queries['q'];
   
    //Split the path of the request into its components
    var pathArray = urlObj.pathname.split("/");

    //Get the last part of the path
    var pathEnd = pathArray[pathArray.length - 1];


    //If path ends with 'games' we return all games
    if(pathEnd === 'games'){
        getTotalGamesCount(response, numItems, offset,query);//This function calls the getAllGames function in its callback
        return;
    }

    if (pathEnd === 'search' && pathArray[pathArray.length - 2] === 'games'){
        getTotalGamesCount(response, numItems, offset,query);//This function calls the getSearchGame function in its callback
        return;
    }

    //If the last part of the path is a valid user id, return data about that user
    var regEx = new RegExp('^[0-9]+$');//RegEx returns true if string is all digits.
    if(regEx.test(pathEnd)){
        getGames(response, pathEnd);//This function calls the getGames function in its callback
        return;
    }

    //The path is not recognized. Return an error message
    response.status(HTTP_STATUS.NOT_FOUND);
    response.send("{error: 'Path not recognized', url: " + request.url + "}");
}


/** Returns all of the games, possibly with a limit on the total number of items returned and the offset (to
 *  enable pagination). This function should be called in the callback of getTotalGamesCount  */
function getAllGames(response, totNumItems, numItems, offset){

    //Select the game data using JOIN to convert foreign keys into useful data.
    var sql = "SELECT product.name, product.id,productimage.id AS imageId,productimage.image FROM (product" +
        " INNER JOIN productimage ON product_id = product.id)";

    //Limit the number of results returned, if this has been specified in the query string
    if(numItems !== undefined && offset !== undefined ){
        sql += " LIMIT " + numItems + " OFFSET " + offset;
    }

    //Execute the query
    db.query(sql, function (err, result) {

        //Check for errors
        if (err){
            //Not an ideal error code, but we don't know what has gone wrong.
            response.status(HTTP_STATUS.INTERNAL_SERVER_ERROR);
            response.json({'error': true, 'message': + err});
            return;
        }

        //Create JavaScript object that combines total number of items with data
        var returnObj = {totNumItems: totNumItems};
        returnObj.data = result; //Array of data from database

        //Return results in JSON format
        response.json(returnObj);
    });
}
/** Returns all of the games with name equal in variable query , possibly with a limit on the total number of items returned and the offset (to
 *  enable pagination). This function should be called in the callback of getTotalGamesCount  */
function getSearchGame(response, totNumItems, numItems, offset,query){

    //Select the game data using JOIN to convert foreign keys into useful data.
    var sql = "SELECT product.name, product.id,productimage.id AS imageId,productimage.image FROM (product INNER JOIN productimage ON product_id = product.id)";

    //search for a game if have been specified in query string
    if(query !== undefined ){
        if(query==="ps4"){
            sql += "Where productimage.format_id = 1";
        }
        else if(query==="xboxone"){
            sql += "Where productimage.format_id = 2";
        }
        else{
            sql += "Where product.name LIKE " + "'%"+query+"%'";
        }
    }
    //Limit the number of results returned, if this has been specified in the query string
    if(numItems !== undefined && offset !== undefined ){
        sql += " LIMIT " + numItems + " OFFSET " + offset;
    }

    //Execute the query
    db.query(sql, function (err, result) {

        //Check for errors
        if (err){
            //Not an ideal error code, but we don't know what has gone wrong.
            response.status(HTTP_STATUS.INTERNAL_SERVER_ERROR);
            response.json({'error': true, 'message': + err});
            return;
        }

        //Create JavaScript object that combines total number of items with data
        var returnObj = {totNumItems: totNumItems};
        returnObj.data = result; //Array of data from database

        //Return results in JSON format
        response.json(returnObj);
    });
}

/** When retrieving all games it start by retrieving the total number of games
    The database callback function will then call the function to get the games data
    with pagination */
function getTotalGamesCount(response, numItems, offset,query){
    //get the total value of the data
    var sql = "SELECT COUNT(*) " + "FROM (product" +
        " INNER JOIN productimage ON product_id = product.id)";

    //search for a game if have been specified in query string and get
    if(query !== undefined ){
        if(query==="ps4"){
            sql += "Where productimage.format_id = 1";
        }
        else if(query==="xboxone"){
            sql += "Where productimage.format_id = 2";
        }
        else{
            sql += "Where product.name LIKE " + "'%"+query+"%'";
        }
    }

    //Execute the query and call the anonymous callback function.
    db.query(sql, function (err, result) {

        //Check for errors
        if (err){
            //Not an ideal error code, but we don't know what has gone wrong.
            response.status(HTTP_STATUS.INTERNAL_SERVER_ERROR);
            response.json({'error': true, 'message': + err});
            return;
        }

        //Get the total number of items from the result
        var totNumItems = result[0]['COUNT(*)'];

        if(query !== undefined ){
            getSearchGame(response, totNumItems, numItems, offset,query)
        }
        else {
            //Call the function that retrieves all games
            getAllGames(response, totNumItems, numItems, offset);
        }


    });
}
/** Returns the game with specified ID */
function getGames(response, id) {
        //Build SQL query to select game with specified id.
        var sql = "SELECT product.name,retailerprice.url, retailer.name AS retailer,productimage.image,retailerprice.price,retailer.id FROM (((retailerprice" +
        " INNER JOIN product ON retailerprice.product_id = product.id)" +
        " INNER JOIN retailer ON retailerprice.retailer_id = retailer.id)" +
        " INNER JOIN productimage ON retailerprice.productimage_id = productimage.id) WHERE productimage.id="+id;

        //Execute the query
        db.query(sql, function (err, result) {

            //Check for errors
            if (err) {
                //Not an ideal error code, but we don't know what has gone wrong.
                response.status(HTTP_STATUS.INTERNAL_SERVER_ERROR);
                response.json({'error': true, 'message': +err});
                return;
            }

            //Output results in JSON format
            response.json(result);
        });

}
