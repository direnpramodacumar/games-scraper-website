
//Import the mysql module
var mysql = require('mysql');

//Create a connection object with the user details
var connectionPool = mysql.createPool({
    connectionLimit: 1,
    host: "localhost",
    user: "DP",
    password: "12345",
    database: "price_comparison",
    debug: false
});

module.exports = connectionPool;
