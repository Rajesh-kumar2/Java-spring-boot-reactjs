import { combineReducers } from 'redux'
import errorReducer from './errorReducer'
import projectReduce from './projectReduce'

export default combineReducers({
  errors: errorReducer,
  project: projectReduce
})
