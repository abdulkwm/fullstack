import React from 'react'
const env_file = process.env.API;
const StudentList = () => {
  return (
    <div>`StudentList + ${env_file}`</div>
  )
}

export default StudentList;