var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/plus', function(req, res, next) {
    var result = parseInt(req.query.a) + parseInt(req.query.b);
    console.log("result is " + result);
    res.json(result);
});

module.exports = router;