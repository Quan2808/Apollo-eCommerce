import React, { Fragment } from "react";
import { forest } from "../../../assets";

function ErrorContent() {
  return (
    <Fragment>
      <div className="flex flex-col my-[30px] ml-[500px]">
        <img
          src="https://images-na.ssl-images-amazon.com/images/G/01/error/en_US/title._TTD_.png"
          style={{ overflowClipMargin: "content-box" }}
          className="max-w-[40%] border-none overflow-clip"
          alt="404 error"
        />
        <img
          style={{ overflowClipMargin: "content-box" }}
          className="max-w-[60%] border-none overflow-clip"
          src={forest}
          alt="forest"
        />
      </div>
    </Fragment>
  );
}

export default ErrorContent;
