'use strict'

class CodedError extends Error {
  constructor (message = 'Unknown error', code = -1) {
    super(message)
    this.code = code
  }
}

module.exports = {
  CodedError,
  ForbiddenError: class ForbiddenError extends CodedError {
    constructor (message = 'Permission denied') {
      super(message, 403)
    }
  },
  InvalidQueryError: class InvalidQueryError extends CodedError {
    constructor (message = 'Invalid parameter') {
      super(message, 400)
    }
  }
}
