module.exports = {
    name: 'mainStructure',
    schema: {
        UUID: {type: String, unique: true, index: true}, // UUID
        faceData: Array, // 人脸数据集
    }
}