import React, { Fragment } from "react";
import { logoBlack } from "../assets";
import { Link, useLocation } from "react-router-dom";

export function useQuery() {
  return new URLSearchParams(useLocation().search);
}

function Confirm() {
  let query = useQuery();
  let status = query.get("status").toString();

  const isSuccess = status === "success";

  return (
    <Fragment>
      {isSuccess ? (
        <div className="font-bodyFont bg-white">
          <div className="flex items-center justify-center mt-10">
            <Link to="/">
              <img
                className="w-[112px] h-[40px]"
                src={logoBlack}
                alt="logo"
              ></img>
            </Link>
          </div>
          <div className="grid grid-cols-3 mt-2">
            <div className="mx-2"></div>
            <div className="bg-white text-center">
              <p>Congratulation! Your registration is confirmed!</p>
              <p>
                <Link
                  className="font-medium hover:text-cyan-300 hover:underline"
                  to="/signin"
                >
                  Sign in
                </Link>{" "}
                and start explore on Apollo
              </p>
            </div>
            <div className="mx-2"></div>
          </div>
        </div>
      ) : (
        <div className="font-bodyFont bg-white">
          <div className="flex items-center justify-center mt-10">
            <Link to="/">
              <img
                className="w-[112px] h-[40px]"
                src={logoBlack}
                alt="logo"
              ></img>
            </Link>
          </div>
          <div className="grid grid-cols-3 mt-2">
            <div className="mx-2"></div>
            <div className="bg-white text-center">
              <p>
                Oops something went wrong! Your confirmation link might be
                invalid or have been expired.
              </p>
              <p>
                Please{" "}
                <Link
                  className="font-medium hover:text-cyan-300 hover:underline"
                  to="/signin"
                >
                  register
                </Link>{" "}
                again
              </p>
            </div>
            <div className="mx-2"></div>
          </div>
        </div>
      )}
    </Fragment>
  );
}

export default Confirm;
