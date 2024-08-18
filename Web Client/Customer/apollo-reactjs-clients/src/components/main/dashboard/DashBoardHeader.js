import React from 'react'
import { logoAdmin } from "../../../assets";
import { Link, useNavigate } from 'react-router-dom';
import { useSelector } from 'react-redux';

function Header() {
  const navigate = useNavigate();
  const adminInfo = useSelector((state) => state.admin.adminInfo);
  console.log(adminInfo);

  return (
    <div className="w-full bg-white border-b-2 text-amazon_blue px-4 py-1 flex justify-between items-center gap-4 sticky top-0 z-50 h-20">
      {/* Logo start */}
      <div onClick={() => navigate("/admin")} className="">
        <img className="max-h-20 h-auto" src={logoAdmin} alt="logo-dasboard"></img>
      </div>
      {/* Logo end */}

      {adminInfo ? (
        <div className='flex flex-row items-center pl-20'>
          {/* <img className='h-8 w-8 rounded-full mr-1' src={adminInfo.avatar} /> */}
          <img className='h-8 w-8 rounded-full mr-1' src='https://cdn-icons-png.flaticon.com/512/1053/1053244.png' />
          <p className="text-xs text-lightText font-light">
            Hello, {adminInfo.adminName}
          </p>
        </div>

      ) : (
        <div>
          <div onClick={() => navigate("/admincentral/register")} className="mt-0 mr-8">
            <button className="pt-2 pb-2 pl-4 pr-4 bg-amber-400 font-bodyFont font-bold hover hover:border-1 rounded-full">Sign up</button>
          </div>
            <div onClick={() => navigate("/admincentral/signin")} className="mt-0 mr-8">
              <button className="pt-2 pb-2 pl-4 pr-4 bg-amber-400 font-bodyFont font-bold hover hover:border-1 rounded-full">Sign in</button>
            </div>
        </div>

      )}
      {/* Signin end */}

    </div>
  )
}

export default Header
