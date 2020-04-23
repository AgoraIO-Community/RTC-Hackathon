'use strict'

const Router = require('koa-router')
const controllers = require('../controllers')

const router = new Router()
router.prefix('/api')

router.post('/addFacialFeatures', controllers.addFacialFeatures.addFacialFeatures)
router.post('/faceIdentification', controllers.faceIdentification.faceIdentification)
router.post('/getEmotion', controllers.getEmotion.getEmotion)

module.exports = router
