import React from 'react'
import NavBar from './NavBar'
import {useSelector} from 'react-redux';

function Admin({children}) {
    const adminInfo = useSelector((state) => state.admin.adminInfo);
    console.log(adminInfo);

    return (<div>
            <div className="">
                {adminInfo ? <NavBar/> : <></>}
                <div className=" p-4 bg-gray-200">
                    {children}
                </div>
            </div>

        </div>)
}

export default Admin
