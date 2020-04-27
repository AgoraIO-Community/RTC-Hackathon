const Structure = require('../models').getModel('mainStructure');

const facialFeatures = {
    Structure,

    async add (userData) {
        const result = await new Structure(userData).save();
        return result;
    },

    async update(query, userData) {
        const result = await Structure.update({...query}, {...userData});
        return result;
    },

    async findOne(query) {
        const result = await Structure.findOne(query);
        return result;
    }
};

module.exports = facialFeatures;
