import React, { useEffect, useRef, useState } from "react";
import MenuIcon from "@mui/icons-material/Menu";
import CloseIcon from "@mui/icons-material/Close";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import SideNavContent from "./SideNavContent";
import {motion} from "framer-motion";
import { Link } from "react-router-dom";
import { useSelector } from "react-redux";

function HeaderBottom() {
  const userInfo = useSelector((state) => state.user.userInfo);
  const [sideBar, setSidebar] = useState(false);
  const motionDivRef = useRef();

  useEffect(() =>{
    function hideSideBar(e){
      if (e.target.contains(motionDivRef.current)) {
        setSidebar(false);
      }
    }
    
    document.body.addEventListener("click", (e) =>{
      hideSideBar(e)
    })

    return document.body.removeEventListener("click", (e) => {
      hideSideBar(e)
    });
  },[])

  return (
    <div className="w-full px-4 h-[36px] bg-amazon_light text-white flex items-center">
      {/* ListItem start */}
      <ul className="flex items-center gap-2 text-sm tracking-wide">
        <li
          onClick={() => {
            setSidebar(true);
          }}
          className="headerHover flex items-center gap-1"
        >
          <MenuIcon />
          All
        </li>
        <li className="headerHover hidden md:inline-flex">Today's Deal</li>
        <li className="headerHover hidden md:inline-flex">Customer Service</li>
        <li className="headerHover hidden md:inline-flex">Gift Cards</li>
        <li className="headerHover hidden md:inline-flex">Registry</li>
        <Link to="/admin"><li className="headerHover hidden md:inline-flex">Admin</li></Link>
      </ul>
      {/* ListItem end */}

      {/* SideNav start */}
      {sideBar && (
        <div
          className="w-full h-screen text-black fixed top-0 left-0 bg-amazon_blue
            bg-opacity-50"
        >
          <div className="w-full h-full relative z-50">
            <motion.div
              ref={motionDivRef}
              initial={{ x: -500, opacity: 0 }}
              animate={{ x: 0, opacity: 1 }}
              transition={{ duration: 0.5 }}
              className="w-[80%] md:w-[350px] h-full bg-white border border-black overflow-y-auto"
            >
              <div
                className="w-full bg-amazon_light text-white py-2 px-6 flex items-center
              gap-4"
              >
                <AccountCircleIcon />
                {userInfo ? (
                  <h3 className="font-titleFont font-bold text-lg tracking-wide hover:text-blue-300 hover:underline underline-offset-1">
                    Hello, {userInfo.clientName}
                  </h3>
                ) : (
                  <Link to="/signin">
                    <h3 className="font-titleFont font-bold text-lg tracking-wide hover:text-blue-300 hover:underline underline-offset-1">
                      Hello, Sign In
                    </h3>
                  </Link>
                )}
              </div>
              <SideNavContent
                title="Digital Content & Devices"
                one="Amazon Music"
                two="Kindle E-readders & Books"
                three="Amazon Appstore"
              />
              <SideNavContent
                title="Shop By Department"
                one="Electronics"
                two="Computers"
                three="Smart Home"
              />
              <SideNavContent
                title="Programs & Features"
                one="Gift Cards"
                two="Amazon live"
                three="International Shopping"
              />
              <SideNavContent
                title="Help & Settings"
                one="Your Account"
                two="Customer Service"
                three="Contact Us"
              />
              <span
                onClick={() => {
                  setSidebar(false);
                }}
                className="cursor-pointer absolute top-0 left-[82%] md:left-[360px]
            w-10 h-10 text-black flex items-center justify-center border bg-gray-200 hover:bg-red-500
            hover:text-white duration-300"
              >
                <CloseIcon />
              </span>
            </motion.div>
          </div>
        </div>
      )}
      {/* SideNav end */}
    </div>
  );
}

export default HeaderBottom;
