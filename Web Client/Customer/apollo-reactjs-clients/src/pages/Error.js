import React from 'react'
import ErrorHeader from '../components/common/error/ErrorHeader';
import ErrorContent from "../components/common/error/ErrorContent";

function Error() {
  return (
    <div className="font-bodyFont bg-white">
      <ErrorHeader />
      <ErrorContent />
    </div>
  );
}

export default Error