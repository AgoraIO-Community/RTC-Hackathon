const fs = require('fs');
const path = require('path');
const mongoose = require('mongoose');
const config = require('../config')
const { logger } = require('../middlewares/logger')

let url = "mongodb://" + config.mongoDB.host + ":" + config.mongoDB.port + "/" + config.mongoDB.database;
var mongo = mongoose.createConnection(url);

let db = {
    mongoose: mongoose,
    mongo: mongo,
    models: {}
};
// 错误
mongo.on('error', function (err) {
    logger.error(new Error(err));
});
// 开启
mongo.once('open', function () {
    logger.info("mongo is opened");
});

fs.readdirSync(__dirname)
    .filter(function (file) {
        return (file.indexOf(".") !== 0) && (file !== "index.js");
    }).forEach(function (file) {
    var modelFile = require(path.join(__dirname, file));
    var schema = new mongoose.Schema(modelFile.schema);

    db.models[modelFile.name] = mongo.model(modelFile.name, schema, modelFile.name);
});

db.getModel = function (name) {
    return this.models[name];
};

module.exports = db;
