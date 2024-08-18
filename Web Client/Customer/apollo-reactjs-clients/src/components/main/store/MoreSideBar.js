import React, { Fragment } from "react";
import { useEffect, useRef } from "react";
import { useDispatch, useSelector } from "react-redux";
import { motion } from "framer-motion";
import { useParams } from "react-router-dom";
import { Link } from "react-router-dom";
import {
  changeCategory,
  changeSubCategory,
  toggleMoreSideBar,
  toggleMoreCategory,
  setSelectedCategory,
  setSelectedSubCategory,
  setSelectedCurrent,
  addMoreCategoryToggle,
  toggleOffMoreCategory,
  setStoreBanner,
  toggleOffMoreCategoryForDeals,
  changeBannerImage,
} from "../../../features/adminStore/adminStoreSlice";

function MoreSideBar() {
  const moreSideBar = useSelector((state) => state.adminStore.moreSideBar);
  const params = useParams();
  const moreCategoryToggle = useSelector(
      (state) => state.adminStore.moreCategoryToggle
  );
  const categories = useSelector((state) => state.adminStore.categories);
  const storeInfo = useSelector((state) => state.adminStore.storeInfo);
  const selectedCategory = useSelector(
      (state) => state.adminStore.selectedCategory
  );
  const selectedSubCategory = useSelector(
      (state) => state.adminStore.selectedSubCategory
  );
  const motionMoreDivRef = useRef();
  const dispatch = useDispatch();

  useEffect(() => {
    function hideMoreSidebar(e) {
      if (e.target.contains(motionMoreDivRef.current)) {
        dispatch(toggleMoreSideBar(false));
      }
    }

    document.body.addEventListener("click", (e) => {
      hideMoreSidebar(e);
    });

    categories
        .filter((category) => category.parentStoreCategory === null)
        .map((category) => dispatch(addMoreCategoryToggle(category.id)));

    return document.body.removeEventListener("click", (e) => {
      hideMoreSidebar(e);
    });
  }, [params.id]);

  const toggleOffOtherCategory = () => {
    categories
        .filter((category) => category.parentStoreCategory === null)
        .map((category) => dispatch(toggleOffMoreCategoryForDeals(category.id)));
  };

  return (
      <Fragment>
        {moreSideBar && (
            <div
                className="w-full font-shopFont h-screen text-black fixed top-0 right-0 bg-amazon_blue
            bg-opacity-50 z-40"
            >
              <motion.div
                  ref={motionMoreDivRef}
                  initial={{ x: 600, y: 0, opacity: 0 }}
                  animate={{ x: 0, opacity: 1 }}
                  transition={{ duration: 0.5 }}
              >
                <div className="fixed font-shopFont top-0 right-0 w-[380px] h-screen p-4 overflow-y-auto bg-white dark:bg-gray-800">
                  <a
                      onClick={() => {
                        dispatch(toggleMoreSideBar(false));
                      }}
                      className="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group"
                  >
                    <svg
                        className="w-6 h-6 text-gray-800 dark:text-white"
                        aria-hidden="true"
                        xmlns="http://www.w3.org/2000/svg"
                        fill="none"
                        viewBox="0 0 8 14"
                    >
                      <path
                          stroke="currentColor"
                          strokeLinecap="round"
                          strokeLinejoin="round"
                          strokeWidth="2"
                          d="M7 1 1.3 6.326a.91.91 0 0 0 0 1.348L7 13"
                      />
                    </svg>
                    <span className="ml-3">Back to Samsung</span>
                  </a>

                  <div className="py-4 overflow-y-auto">
                    <ul className="space-y-2 font-medium">
                      <li>
                        <Link
                            onClick={() => {
                              toggleOffOtherCategory();
                              dispatch(toggleMoreSideBar(false));
                              dispatch(changeCategory(""));
                              dispatch(changeSubCategory(""));
                              dispatch(setStoreBanner(storeInfo.homeImage));
                            }}
                            className="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group"
                            to={`/store/${storeInfo.id}`}
                        >
                          <span className="ml-3 font-light">Home</span>
                        </Link>
                      </li>
                      <li>
                        <button
                            onClick={() => {
                              toggleOffOtherCategory();
                              dispatch(toggleMoreCategory("deals"));
                            }}
                            type="button"
                            className="flex items-center w-full p-2 text-base text-gray-900 transition duration-75 rounded-lg group hover:bg-gray-100 dark:text-white dark:hover:bg-gray-700"
                        >
                      <span className="flex-1 ml-3 text-left whitespace-nowrap font-light">
                        Deals
                      </span>
                          <svg
                              className="w-3 h-3"
                              aria-hidden="true"
                              xmlns="http://www.w3.org/2000/svg"
                              fill="none"
                              viewBox="0 0 10 6"
                          >
                            <path
                                stroke="currentColor"
                                strokeLinecap="round"
                                strokeLinejoin="round"
                                strokeWidth="2"
                                d="m1 1 4 4 4-4"
                            />
                          </svg>
                        </button>
                        {moreCategoryToggle.deals && (
                            <ul className="py-2 space-y-2">
                              <li>
                                <Link
                                    to={`/store/${storeInfo.id}/deals`}
                                    onMouseOver={() => {
                                      dispatch(changeBannerImage(storeInfo.dealsImage));
                                    }}
                                    onClick={() => {
                                      dispatch(changeCategory("Deals"));
                                      dispatch(changeSubCategory(""));
                                      dispatch(setStoreBanner(storeInfo.dealsImage));
                                      dispatch(toggleMoreSideBar(false));
                                    }}
                                    className="flex items-center font-medium w-full p-2 text-gray-900 transition duration-75 rounded-lg pl-11 group hover:bg-gray-100 dark:text-white dark:hover:bg-gray-700"
                                >
                                  Deals
                                </Link>
                              </li>
                              {categories
                                  .filter(
                                      (category) => category.parentStoreCategory === null
                                  )
                                  .map((category) => (
                                      <li key={category.id}>
                                        <a
                                            onClick={() => {
                                              dispatch(changeCategory("Deals"));
                                              dispatch(changeSubCategory(category.name));
                                              dispatch(setStoreBanner(category.heroImage));
                                            }}
                                            className="flex cursor-pointer items-center font-light w-full p-2 text-gray-900 transition duration-75 rounded-lg pl-11 group hover:bg-gray-100 dark:text-white dark:hover:bg-gray-700"
                                        >
                                          {category.name}
                                        </a>
                                      </li>
                                  ))}
                            </ul>
                        )}
                      </li>

                      {categories
                          .filter((category) => category.parentStoreCategory === null)
                          .map((category) => (
                              <li key={category.id}>
                                <button
                                    name={category.name}
                                    type="button"
                                    onClick={() => {
                                      categories
                                          .filter(
                                              (category) =>
                                                  category.parentStoreCategory === null
                                          )
                                          .map((categoryCheck) =>
                                              categoryCheck.id !== category.id
                                                  ? dispatch(
                                                      toggleOffMoreCategory(categoryCheck.id)
                                                  )
                                                  : ""
                                          );
                                      dispatch(setSelectedCategory(category.name));
                                      dispatch(toggleMoreCategory(category.id));
                                    }}
                                    className="flex items-center w-full p-2 text-base text-gray-900 transition duration-75 rounded-lg group hover:bg-gray-100 dark:text-white dark:hover:bg-gray-700"
                                >
                          <span className="flex-1 ml-3 text-left whitespace-nowrap font-light">
                            {category.name}
                          </span>
                                  <svg
                                      className="w-3 h-3"
                                      aria-hidden="true"
                                      xmlns="http://www.w3.org/2000/svg"
                                      fill="none"
                                      viewBox="0 0 10 6"
                                  >
                                    <path
                                        stroke="currentColor"
                                        strokeLinecap="round"
                                        strokeLinejoin="round"
                                        strokeWidth="2"
                                        d="m1 1 4 4 4-4"
                                    />
                                  </svg>
                                </button>

                                {moreCategoryToggle[category.id] && (
                                    <ul className="py-2 space-y-2">
                                      <li>
                                        <Link
                                            to={`/store/${storeInfo.id}/${selectedCategory}`}
                                            onMouseOver={() => {
                                              dispatch(
                                                  changeBannerImage(category.heroImage)
                                              );
                                            }}
                                            onClick={() => {
                                              dispatch(setSelectedCurrent(category.name));
                                              dispatch(setStoreBanner(category.heroImage));
                                              dispatch(changeCategory(selectedCategory));
                                              dispatch(changeSubCategory(""));
                                              dispatch(toggleMoreSideBar(false));
                                            }}
                                            className="flex items-center font-medium w-full p-2 text-gray-900 transition duration-75 rounded-lg pl-11 group hover:bg-gray-100 dark:text-white dark:hover:bg-gray-700"
                                        >
                                          {category.name}
                                        </Link>
                                      </li>
                                      {categories.map(
                                          (category) =>
                                              category.parentStoreCategory !== null &&
                                              category.parentStoreCategory.name ===
                                              selectedCategory && (
                                                  <li key={category.id}>
                                                    <Link
                                                        name={category.name}
                                                        onMouseOver={() => {
                                                          dispatch(
                                                              setSelectedSubCategory(category.name)
                                                          );
                                                        }}
                                                        onClick={(e) => {
                                                          dispatch(
                                                              setSelectedCurrent(category.name)
                                                          );
                                                          dispatch(
                                                              setStoreBanner(category.heroImage)
                                                          );
                                                          dispatch(
                                                              changeCategory(selectedCategory)
                                                          );
                                                          dispatch(
                                                              changeSubCategory(e.target.name)
                                                          );
                                                          dispatch(toggleMoreSideBar(false));
                                                        }}
                                                        className="flex items-center cursor-pointer font-light w-full p-2 text-gray-900 transition duration-75 rounded-lg pl-11 group hover:bg-gray-100 dark:text-white dark:hover:bg-gray-700"
                                                        to={`/store/${storeInfo.id}/${selectedCategory}/${selectedSubCategory}`}
                                                    >
                                                      {category.name}
                                                    </Link>
                                                  </li>
                                              )
                                      )}
                                    </ul>
                                )}
                              </li>
                          ))}
                    </ul>
                  </div>
                </div>
              </motion.div>
            </div>
        )}
      </Fragment>
  );
}

export default MoreSideBar;
