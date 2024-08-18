import React, {useEffect, useState} from "react";
import {logoBlack} from "../assets";
import ArrowRightIcon from "@mui/icons-material/ArrowRight";
import {Link, useNavigate} from "react-router-dom";
import {Formik} from "formik";
import {RotatingLines} from "react-loader-spinner";
import axios from "axios";
import {motion} from "framer-motion";
import {useDispatch, useSelector} from "react-redux";
import {setUserInfo} from "../features/user/userSlice";
import jwt_decode from "jwt-decode";
import {addNewCartLine, createSaveForLater, resetCart, resetSaveForLater} from "../features/cart/cartSlice";

function Signin() {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const [form, setForm] = useState({});
    const [loading, setLoading] = useState(false);
    const [errorNotify, setErrorNotify] = useState("");
    const [successNotify, setSuccessNotify] = useState("");
    const {userInfo} = useSelector((state) => state.user);
    const {products, empties} = useSelector((state) => state.cart);

    const handleValidate = () => {
        let errors = {};
        if (!form.email) {
            errors.email = "Required";
        }

        if (!form.password) {
            errors.password = "Required";
        }

        return errors;
    };

    const handleChange = (event) => {
        setForm({
            ...form, [event.target.name]: event.target.value,
        });
    };

    const handleSubmit = async () => {
        setLoading(true);
        try {
            const response = await axios.post("http://localhost:9999/api/login", form);

            setErrorNotify("");  // Clear any previous error notification
            setLoading(false);   // Stop loading indicator

            const accessToken = response.data.accessToken;
            window.localStorage.setItem("token", accessToken);  // Store the access token

            // Decode token and set user info
            const userInfo = jwt_decode(accessToken).sub;  // Ensure 'sub' contains correct user info
            dispatch(setUserInfo(userInfo));

            // Success notification
            setSuccessNotify("Log in successfully! Welcome back");

            // Redirect after a delay
            setTimeout(() => {
                navigate("/");
            }, 2500);

        } catch (err) {
            setLoading(false);  // Stop loading indicator
            setErrorNotify("Invalid email or password");  // Show error notification

            // Log the error for debugging
            console.error("Login failed:", err);
        }
    };


    useEffect(() => {
        if (userInfo) {
            sendValuesInDatabase();
        }
    }, [userInfo])

    const sendValuesInDatabase = () => {
        products.map((item) => dispatch(addNewCartLine({
            id: '', quantity: item.quantity, cartId: userInfo.id, variantId: item.variantDto.id,
        })));
        empties.map((item) => dispatch(createSaveForLater({
            id: '', quantity: item.quantity, cartId: userInfo.id, variantId: item.variantDto.id,
        })));
        dispatch(resetCart());
        dispatch(resetSaveForLater());
    };

    return (<div className="w-full font-bodyFont">
        <div className="w-full bg-gray-100 pb-10">
            <Formik
                initialValues={form}
                validate={handleValidate}
                onSubmit={handleSubmit}
            >
                {({errors, handleSubmit}) => (<form
                    onSubmit={handleSubmit}
                    className="w-[350px] mx-auto flex flex-col items-center"
                >
                    <Link to="/">
                        <img className="w-36" src={logoBlack} alt="logo"/>
                    </Link>
                    <div className="w-full bg-gray-100 border border-zinc-300 rounded-md p-6">
                        <h2 className="font-titleFont text-3xl font-medium mb-4">
                            Sign in
                        </h2>
                        <div className="flex flex-col gap-3">
                            <div className="flex flex-col gap-2">
                                <p className="text-sm font-medium">Email</p>
                                <input
                                    onChange={handleChange}
                                    name="email"
                                    className="w-full normal-case py-1 bordder border-zinc-400
                    px-2 text-base rounded-sm outline-none focus-within:border-[#e77600]
                    focus-within:shadow-amazonInput duration-100
                    "
                                    type="email"
                                ></input>
                                {errors.email && (<p
                                    className="text-red-600 text-xs font-semibold tracking-wide
                    flex items-center gap-2 -mt-1.5"
                                >
                        <span className="italic font-titleFont font-extrabold text-base">
                          !
                        </span>
                                    {errors.email}
                                </p>)}
                            </div>
                            <div className="flex flex-col gap-2">
                                <p className="text-sm font-medium">Password</p>
                                <input
                                    onChange={handleChange}
                                    name="password"
                                    className="w-full normal-case py-1 bordder border-zinc-400
                    px-2 text-base rounded-sm outline-none focus-within:border-[#e77600]
                    focus-within:shadow-amazonInput duration-100
                    "
                                    type="password"
                                ></input>
                                {errors.password && (<p
                                    className="text-red-600 text-xs font-semibold tracking-wide
                    flex items-center gap-2 -mt-1.5"
                                >
                        <span className="italic font-titleFont font-extrabold text-base">
                          !
                        </span>
                                    {errors.password}
                                </p>)}
                            </div>
                            {errorNotify && (<p
                                className="text-red-600 text-xs font-semibold tracking-wide
                    flex items-center gap-2 -mt-1.5"
                            >
                      <span className="italic font-titleFont font-extrabold text-base">
                        !
                      </span>
                                {errorNotify}
                            </p>)}
                            <button
                                className="w-full py-1.5 text-sm font-normal
              rounded-sm bg-gradient-to-t from-[#f7dfa5] to-[#f0c14b] hover:bg-gradient-to-b border
              border-zinc-400 active:border-yellow-800 active:shadow-amazonInput"
                                type="submit"
                            >
                                Continue
                            </button>
                            {loading && (<div className="flex justify-center">
                                <RotatingLines
                                    strokeColor="#febd69"
                                    strokeWidth="5"
                                    animationDuration="0.75"
                                    width="50"
                                    visible={true}
                                />
                            </div>)}
                            {successNotify && (<div>
                                <motion.p
                                    initial={{y: 10, opacity: 0}}
                                    animate={{y: 0, opacity: 1}}
                                    transition={{duration: 0.5}}
                                    className="text-base font-titleFont font-semibold text-green-500
                        border-[1px] border-green-500 px-2 text-center"
                                >
                                    {successNotify}
                                </motion.p>
                            </div>)}
                        </div>
                        <p className="text-xs text-black leading-4 mt-4">
                            By Continuing, you agree to Apollo's{" "}
                            <span className="text-blue-600">Conditions of Use </span>
                            and <span className="text-blue-600">Private Notice.</span>
                        </p>
                        <p className="text-xs text-gray-600 mt-4 cursor-pointer group">
                            <ArrowRightIcon/>
                            <span
                                className="text-blue-600 group-hover:text-orange-700 group-hover:underline underline-offset-1 ">
                    Need help?
                  </span>
                        </p>
                    </div>
                    <div className="w-full text-xs text-gray-600 mt-4 flex items-center">
                        <span className="w-1/3 h-[1px] bg-zinc-400 inline-flex"></span>
                        <span className="w-1/3 text-center">New to Apollo?</span>
                        <span className="w-1/3 h-[1px] bg-zinc-400 inline-flex"></span>
                    </div>
                    <Link className="w-full" to="/register">
                        <button
                            className="w-full py-1.5 px-2 mt-4 text-sm font-normal
              rounded-sm bg-gradient-to-t from-slate-200 to-slate-100 hover:bg-gradient-to-b border
              border-zinc-400 active:border-yellow-800 active:shadow-amazonInput"
                        >
                            Create your Apollo account
                        </button>
                    </Link>
                </form>)}
            </Formik>
        </div>
        <div
            className="w-full bg-gradient-to-t from-white via-white to-zinc-200 flex flex-col gap-4 justify-center items-center py-10">
            <div className="flex items-center gap-6">
                <p className="text-xs text-blue-600 hover:text-orange-600 hover:underline underline-offset-1 cursor-pointer duration-100">
                    Conditions of Use
                </p>
                <p className="text-xs text-blue-600 hover:text-orange-600 hover:underline underline-offset-1 cursor-pointer duration-100">
                    Privacy Notice
                </p>
                <p className="text-xs text-blue-600 hover:text-orange-600 hover:underline underline-offset-1 cursor-pointer duration-100">
                    Help
                </p>
            </div>
            <p className="text-xs text-gray-600">
                © 2023-2023 Apollo.com, Inc. or its affiliates
            </p>
        </div>
    </div>);
}

export default Signin;
