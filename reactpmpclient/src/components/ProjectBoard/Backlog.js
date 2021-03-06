import React, { Component } from 'react'
import ProjectTask from './ProjectTasks/ProjectTask'

class Backlog extends Component {
  render () {
    const { project_tasks_props } = this.props
    const tasks = project_tasks_props.map(project_task => (
      <ProjectTask key={project_task.id} project_task={project_task} />
    ))

    let todoItems = []
    let inProgressitems = []
    let doneItem = []
    for (let task of tasks) {
      if (task.props.project_task.status === 'TO_DO') {
        todoItems.push(task)
      }
      if (task.props.project_task.status === 'IN_PROGRESS') {
        inProgressitems.push(task)
      }
      if (task.props.project_task.status === 'DONE') {
        doneItem.push(task)
      }
    }

    return (
      <div className='container'>
        <div className='row'>
          <div className='col-md-4'>
            <div className='card text-center mb-2'>
              <div className='card-header bg-secondary text-white'>
                <h3>TO DO</h3>
              </div>
            </div>
            {todoItems}
          </div>
          <div className='col-md-4'>
            <div className='card text-center mb-2'>
              <div className='card-header bg-primary text-white'>
                <h3>In Progress</h3>
              </div>
            </div>
            {inProgressitems}
          </div>
          <div className='col-md-4'>
            <div className='card text-center mb-2'>
              <div className='card-header bg-success text-white'>
                <h3>Done</h3>
              </div>
            </div>
            {doneItem}
          </div>
        </div>
      </div>
    )
  }
}

export default Backlog
