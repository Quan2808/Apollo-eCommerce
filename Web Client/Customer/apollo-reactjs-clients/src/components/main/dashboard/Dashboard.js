import React from 'react'
import SideBar from './SideBar';
import { SideBarTail } from './SideBarTail';


function Dashboard({ children }) {
  return (
    <div className="grid grid-cols-5">
      <SideBarTail />
      <div className="flex-col p-4 bg-gray-200 col-span-4 mr-4">
        {children}
      </div>
    </div>
  );
};

export default Dashboard;
