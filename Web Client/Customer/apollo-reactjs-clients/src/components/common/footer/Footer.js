import React, { Fragment } from 'react'
import FooterTop from './FooterTop'
import FooterMiddle from './FooterMiddle'
import FooterBottom from './FooterBottom'
import { useSelector } from "react-redux";

function Footer() {
  const userInfo = useSelector((state) => state.user.userInfo);

  return (
    <div className='font-titleFont'>
    {userInfo? (
      <Fragment></Fragment>
    ) : (
      <FooterTop />
    )
  }
        <FooterMiddle/>
        <FooterBottom/>
    </div>
  )
}

export default Footer