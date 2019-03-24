import { combineReducers } from 'redux'
import errorReducer from './errorReducer'
import projectReduce from './projectReduce'
import backlogReducer from './backlogReducer'

export default combineReducers({
  errors: errorReducer,
  project: projectReduce,
  backlog: backlogReducer
})
