import React, { useEffect, useState } from "react";
import { logo } from "../../../assets";
import SearchIcon from "@mui/icons-material/Search";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";

function ErrorHeader() {
  const navigate = useNavigate();

  return (
    <div className="w-full sticky top-0 z-50">
      <div className="w-full bg-amazon_blue text-white px-4 py-3 flex items-center gap-4">
        {/* Logo start */}
        <div onClick={() => navigate("/")} className="headerHover">
          <img className="w-[5rem] mt-2" src={logo} alt="logo"></img>
        </div>
        {/* Logo end */}

        {/* Searchbar start */}
        <div className="h-10 rounded-md hidden lgl:flex flex-grow relative">
          <input
            type="text"
            className="h-full text-base text-amazon_blue flex-grow outline-none border-none px-2 rounded-l-md"
            placeholder="Search"
          ></input>
          <span className="w-12 h-full flex items-center justify-center bg-amazon_yellow hover:bg-[#f3a847] duration-300 text-amazon_blue cursor-pointer rounded-tr-md rounded-br-md">
            <SearchIcon />
          </span>
        </div>
        {/* Searchbar end */}
      </div>
      
    </div>
  );
}

export default ErrorHeader;
