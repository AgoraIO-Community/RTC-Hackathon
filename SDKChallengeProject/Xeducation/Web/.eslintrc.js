module.exports = {
  root: true,
  env: {
    node: true
  },
  extends: [
    'plugin:vue/essential',
    '@vue/standard'
  ],
  parserOptions: {
    parser: 'babel-eslint'
  },
  rules: {
    'no-console': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    'space-before-function-paren':0,
    indent:0,
    'key-spacing':0,
    'comma-dangle':0,
    'space-before-blocks':0,
    'object-curly-spacing':0,
    'quotes':0,
    'comma-spacing':0,
    'no-unused-vars':0,
    'no-undef':0,
  },
}
