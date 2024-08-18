import React, { Fragment } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import {
  changeCategory,
  changeSubCategory,
  toggleMoreSideBar,
  setSelectedCategory,
  setSelectedCurrent,
  setStore,
  setCategory,
  setStoreBanner,
} from "../../../features/adminStore/adminStoreSlice";

function HeaderBreadcrumb() {
  const dispatch = useDispatch();
  const breadcrumb = useSelector((state) => state.adminStore.breadcrumb);
  const storeInfo = useSelector((state) => state.adminStore.storeInfo);
  const selectedCategory = useSelector((state) => state.adminStore.selectedCategory);

  return (
    <Fragment>
      <nav className="flex w-full bg-white pl-6">
        <ol className="inline-flex items-center space-x-1 md:space-x-0 max-w-full">
          <li className="inline-flex items-center">
            <Link
              onClick={() => {
                dispatch(changeCategory(""));
                dispatch(changeSubCategory(""));
                dispatch(setStoreBanner(storeInfo.homeImage));
              }}
              className="inline-flex items-center text-xs font-medium text-gray-500 hover:underline dark:text-gray-400 dark:hover:text-white"
              to={`/store/${storeInfo.id}`}
            >
              {storeInfo.name}
            </Link>
          </li>
          {breadcrumb.category && (
            <li>
              <div className="flex items-center">
                <svg
                  className="w-3 h-3 text-gray-400 mx-1"
                  aria-hidden="true"
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 6 10"
                >
                  <path
                    stroke="currentColor"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth="2"
                    d="m1 9 4-4-4-4"
                  />
                </svg>
                <Link
                  onClick={() => {
                    dispatch(setSelectedCurrent(breadcrumb.category));
                    dispatch(setSelectedCategory(breadcrumb.category));
                    dispatch(setStoreBanner(breadcrumb.bannerImage));
                    dispatch(changeCategory(selectedCategory));
                    dispatch(changeSubCategory(""));
                  }}
                  className="inline-flex items-center cursor-pointer text-xs font-medium text-gray-500 hover:underline dark:text-gray-400 dark:hover:text-white"
                  to={`/store/${storeInfo.id}/${selectedCategory}`}
                >
                  {breadcrumb.category}
                </Link>
              </div>
            </li>
          )}
          {breadcrumb.category && breadcrumb.subCategory !== "" && (
            <li>
              <div className="flex items-center">
                <svg
                  className="w-3 h-3 text-gray-400 mx-1"
                  aria-hidden="true"
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 6 10"
                >
                  <path
                    stroke="currentColor"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth="2"
                    d="m1 9 4-4-4-4"
                  />
                </svg>
                <a className="inline-flex items-center cursor-pointer text-xs font-medium text-gray-500 hover:underline dark:text-gray-400 dark:hover:text-white">
                  {breadcrumb.subCategory}
                </a>
              </div>
            </li>
          )}
        </ol>
      </nav>
    </Fragment>
  );
}

export default HeaderBreadcrumb;
