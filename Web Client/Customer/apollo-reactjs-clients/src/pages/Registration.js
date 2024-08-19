import React, {useState} from "react";
import {logoBlack} from "../assets";
import ArrowRightIcon from "@mui/icons-material/ArrowRight";
import {Link, useNavigate} from "react-router-dom";
import {Formik} from "formik";
import {motion} from "framer-motion";
import axios from "axios";
import {RotatingLines} from "react-loader-spinner";

function Registration() {
    const [form, setForm] = useState({});
    const [registerData, setRegisterData] = useState({});
    const [registeredEmail, setRegisteredEmail] = useState(false);
    const [loading, setLoading] = useState(false);
    const [successNotify, setSuccessNotify] = useState("");
    const [emailNotify, setEmailNotify] = useState("");
    const navigate = useNavigate();

    const REGEX = {
        email: /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/,
        password: /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/,
        phoneNumber: /^[0-9]{10}$/,
    };

    const handleChange = (event) => {
        setForm({
            ...form, [event.target.name]: event.target.value,
        });

        if (event.target.name !== "cpassword") {
            setRegisterData({
                ...registerData, [event.target.name]: event.target.value,
            });
        }
    };

    const handleValidate = () => {
        let errors = {};

        if (!form.clientName) {
            errors.clientName = "Required";
        }

        if (!form.email) {
            errors.email = "Required";
        } else if (!REGEX.email.test(form.email)) {
            errors.email = "Invalid email";
        }

        if (!form.password) {
            errors.password = "Required";
        } else if (!REGEX.password.test(form.password)) {
            errors.password = "Password must be minimum 8 characters, at least one number, one letter";
        }

        if (form.cpassword !== form.password) {
            errors.cpassword = "Password does not match";
        }

        // Add phone number validation
        if (!form.phoneNumber) {
            errors.phoneNumber = "Required";
        } else if (!REGEX.phoneNumber.test(form.phoneNumber)) {
            errors.phoneNumber = "Invalid phone number";
        }

        return errors;
    };


    const handleSubmit = async () => {
        setLoading(true);
        setRegisteredEmail(false);
        await axios
            .post("http://localhost:9999/api/register", registerData)
            .then(() => {
                setLoading(false);
                setSuccessNotify("Account created successfully");
                setEmailNotify("A confirmation link has been sent to your email");
                setTimeout(() => {
                    navigate("/signin");
                }, 2500);
            })
            .catch((err) => {
                setRegisteredEmail(true);
                setLoading(false);
                throw err;
            });

        await axios
            .post("http://localhost:9999/api/cart", registerData)
            .then((res) => {
                console.log(res.data);
            })
            .catch((err) => {
                throw err;
            });
    };

    return (<div className="w-full font-bodyFont">
            <div className="w-full bg-gray-100 pb-10">
                <Formik
                    initialValues={form}
                    validate={handleValidate}
                    onSubmit={handleSubmit}
                >
                    {({ errors, handleSubmit }) => (
                        <form
                            onSubmit={handleSubmit}
                            className="w-[350px] mx-auto flex flex-col items-center"
                        >
                            <Link to="/">
                                <img className="w-36" src={logoBlack} alt="logo" />
                            </Link>
                            <div className="w-full border border-zinc-200 bg-gray-100 rounded-md p-6">
                                <h2 className="font-titleFont text-3xl font-medium mb-4">
                                    Create Account
                                </h2>
                                <div className="flex flex-col gap-3">
                                    <div className="flex flex-col gap-2">
                                        <p className="text-sm font-medium">Your name</p>
                                        <input
                                            maxLength="50"
                                            placeholder="First and last name"
                                            onChange={handleChange}
                                            name="clientName"
                                            value={form.clientName || ""}
                                            className="w-full placeholder:normal-case placeholder:text-sm normal-case py-1 bordder border-zinc-400
                        px-2 text-base rounded-sm outline-none focus-within:border-[#e77600]
                        focus-within:shadow-amazonInput duration-100"
                                            type="text"
                                        ></input>
                                        {errors.clientName && (
                                            <p
                                                className="text-red-600 text-xs font-semibold tracking-wide
                                flex items-center gap-2 -mt-1.5"
                                            >
                                <span className="italic font-titleFont font-extrabold text-base">
                                    !
                                </span>
                                                {errors.clientName}
                                            </p>
                                        )}
                                    </div>
                                    <div className="flex flex-col gap-2">
                                        <p className="text-sm font-medium">Email</p>
                                        <input
                                            name="email"
                                            onChange={handleChange}
                                            value={form.email || ""}
                                            className="w-full normal-case py-1 bordder border-zinc-400
                            px-2 text-base rounded-sm outline-none focus-within:border-[#e77600]
                            focus-within:shadow-amazonInput duration-100"
                                            type="text"
                                        ></input>
                                        {errors.email && (
                                            <p
                                                className="text-red-600 text-xs font-semibold tracking-wide
                                flex items-center gap-2 -mt-1.5"
                                            >
                                <span className="italic font-titleFont font-extrabold text-base">
                                    !
                                </span>
                                                {errors.email}
                                            </p>
                                        )}
                                        {registeredEmail && (
                                            <p
                                                className="text-red-600 text-xs font-semibold tracking-wide
                                flex items-center gap-2 -mt-1.5"
                                            >
                                <span className="italic font-titleFont font-extrabold text-base">
                                    !
                                </span>
                                                Email has already been registered
                                            </p>
                                        )}
                                    </div>
                                    <div className="flex flex-col gap-2">
                                        <p className="text-sm font-medium">Phone Number</p>
                                        <input
                                            name="phoneNumber"
                                            onChange={handleChange}
                                            value={form.phoneNumber || ""}
                                            placeholder="Enter your phone number"
                                            className="w-full placeholder:normal-case placeholder:text-sm normal-case py-1 bordder border-zinc-400
                            px-2 text-base rounded-sm outline-none focus-within:border-[#e77600]
                            focus-within:shadow-amazonInput duration-100"
                                            type="text"
                                        ></input>
                                        {errors.phoneNumber && (
                                            <p
                                                className="text-red-600 text-xs font-semibold tracking-wide
                                flex items-center gap-2 -mt-1.5"
                                            >
                                <span className="italic font-titleFont font-extrabold text-base">
                                    !
                                </span>
                                                {errors.phoneNumber}
                                            </p>
                                        )}
                                    </div>
                                    <div className="flex flex-col gap-2">
                                        <p className="text-sm font-medium">Password</p>
                                        <input
                                            name="password"
                                            onChange={handleChange}
                                            value={form.password || ""}
                                            placeholder="8 characters, one number, one letter"
                                            className="w-full placeholder:normal-case placeholder:text-sm normal-case py-1 bordder border-zinc-400
                            px-2 text-base rounded-sm outline-none focus-within:border-[#e77600]
                            focus-within:shadow-amazonInput duration-100"
                                            type="password"
                                        ></input>
                                        {errors.password && (
                                            <p
                                                className="text-red-600 text-xs font-semibold tracking-wide
                                flex items-center gap-2 -mt-1.5"
                                            >
                                <span className="italic font-titleFont font-extrabold text-base">
                                    !
                                </span>
                                                {errors.password}
                                            </p>
                                        )}
                                    </div>
                                    <div className="flex flex-col gap-2">
                                        <p className="text-sm font-medium">Re-enter Password</p>
                                        <input
                                            name="cpassword"
                                            onChange={handleChange}
                                            value={form.cpassword || ""}
                                            className="w-full normal-case py-1 bordder border-zinc-400
                            px-2 text-base rounded-sm outline-none focus-within:border-[#e77600]
                            focus-within:shadow-amazonInput duration-100"
                                            type="password"
                                        ></input>
                                        {errors.cpassword && (
                                            <p
                                                className="text-red-600 text-xs font-semibold tracking-wide
                                flex items-center gap-2 -mt-1.5"
                                            >
                                <span className="italic font-titleFont font-extrabold text-base">
                                    !
                                </span>
                                                {errors.cpassword}
                                            </p>
                                        )}
                                    </div>
                                    <button
                                        type="submit"
                                        className="w-full py-1.5 text-sm font-normal
                            rounded-sm bg-gradient-to-t from-[#f7dfa5] to-[#f0c14b] hover:bg-gradient-to-b border
                            border-zinc-400 active:border-yellow-800 active:shadow-amazonInput"
                                    >
                                        Continue
                                    </button>
                                    {loading && (
                                        <div className="flex justify-center">
                                            <RotatingLines
                                                strokeColor="#febd69"
                                                strokeWidth="5"
                                                animationDuration="0.75"
                                                width="50"
                                                visible={true}
                                            />
                                        </div>
                                    )}
                                    {successNotify && emailNotify && (
                                        <div>
                                            <div>
                                                <motion.p
                                                    initial={{ y: 10, opacity: 0 }}
                                                    animate={{ y: 0, opacity: 1 }}
                                                    transition={{ duration: 0.5 }}
                                                    className="text-base font-titleFont font-semibold text-green-500
                                    border-[1px] border-green-500 px-2 text-center"
                                                >
                                                    {successNotify}
                                                </motion.p>
                                            </div>
                                            <div>
                                                <motion.p
                                                    initial={{ y: 10, opacity: 0 }}
                                                    animate={{ y: 0, opacity: 1 }}
                                                    transition={{ duration: 0.5 }}
                                                    className="text-xs font-titleFont font-semibold text-green-500
                                    border-[1px] border-green-500 px-2 text-center mt-2"
                                                >
                                                    {emailNotify}
                                                </motion.p>
                                            </div>
                                        </div>
                                    )}
                                </div>
                                <p className="text-xs text-black leading-4 mt-4">
                                    By creating an account, you agree to Apollo's{" "}
                                    <span className="text-blue-600">Conditions of Use </span>
                                    and <span className="text-blue-600">Private Notice.</span>
                                </p>
                                <div>
                                    <p className="text-xs text-black">
                                        Already have an account?{" "}
                                        <Link to="/signin">
                            <span
                                className="text-xs text-blue-600 hover:text-orange-600
                                hover:underline underline-offset-1 cursor-pointer duration-100"
                            >
                                Sign in{" "}
                                <span>
                                    <ArrowRightIcon />
                                </span>
                            </span>
                                        </Link>
                                    </p>
                                </div>
                            </div>
                        </form>
                    )}
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

export default Registration;
