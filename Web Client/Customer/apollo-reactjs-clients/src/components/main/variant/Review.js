import React, { useEffect } from "react";
import {
    Rating,
    Typography,
    Card,
    Input,
    Checkbox,
    Button
} from "@material-tailwind/react";
import { useDispatch } from "react-redux";
import { addReview } from "../../../features/coment_review/reviewSlide"


export function Review() {
    const { userInfo } = useSelector((state) => state.user);
    const { variant } = useSelector((state) => state.variant.variantDetail);
    const [star, setStar] = React.useState(4);
    const [title, setTitle] = React.useState("");
    const [content, setContent] = React.useState("");
    const dispatch = useDispatch();


    const submit = () => {
        const review = {
            star: star,
            title: title,
            content: content
        }
        dispatch(addReview({ review: review, userId: userInfo.id, variantId: variant.id }))
    }

return (
    <div>
        <div className="flex items-center gap-2 font-bold text-blue-gray-500">
            {rated}.0
            <Rating value={4} onChange={(value) => setRated(value)} />
            <Typography color="blue-gray" className="font-medium text-blue-gray-500">
                Based on 134 Reviews
            </Typography>
        </div>
        <Card color="transparent" shadow={false}>
            <Typography variant="h4" color="blue-gray">
                Review
            </Typography>
            <Typography color="gray" className="mt-1 font-normal">
                Nice to meet you! Enter your details to review.
            </Typography>
            <form className="mt-8 mb-2 w-80 max-w-screen-lg sm:w-96">
                <div className="mb-1 flex flex-col gap-6">
                    <Typography variant="h6" color="blue-gray" className="-mb-3">
                        Title
                    </Typography>
                    <Input
                        size="lg"
                        placeholder="Enter the hot title"
                        className=" !border-t-blue-gray-200 focus:!border-t-gray-900"
                        labelProps={{
                            className: "before:content-none after:content-none",
                        }}
                        value={title}
                        onChange={(event) => setTitle(event.target.value)}
                    />
                    <Typography variant="h6" color="blue-gray" className="-mb-3">
                        Content
                    </Typography>
                    <Input
                        size="lg"
                        placeholder="Your review is very important for us to improve our service"
                        className=" !border-t-blue-gray-200 focus:!border-t-gray-900"
                        labelProps={{
                            className: "before:content-none after:content-none",
                        }}
                        value={content}
                        onChange={(event) => setContent(event.target.value)}
                    />
                    <Typography variant="h6" color="blue-gray" className="-mb-3">
                        Password
                    </Typography>
                    <Input
                        type="password"
                        size="lg"
                        placeholder="********"
                        className=" !border-t-blue-gray-200 focus:!border-t-gray-900"
                        labelProps={{
                            className: "before:content-none after:content-none",
                        }}
                    />
                    {/* image for review
                        B1: UPLOAD
                        B2: RENDER IMAGE/VIDEO */}
                    <figure className="relative h-96 w-full">
                        <img
                            className="h-full w-full rounded-xl object-cover object-center"
                            src="https://images.unsplash.com/photo-1682407186023-12c70a4a35e0?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2832&q=80"
                            alt="nature image"
                        />
                        <figcaption className="absolute bottom-8 left-2/4 flex w-[calc(100%-4rem)] -translate-x-2/4 justify-between rounded-xl border border-white bg-white/75 py-4 px-6 shadow-lg shadow-black/5 saturate-200 backdrop-blur-sm">
                            <div>
                                <Typography variant="h5" color="blue-gray">
                                    Sara Lamalo
                                </Typography>
                                <Typography color="gray" className="mt-2 font-normal">
                                    20 July 2022
                                </Typography>
                            </div>
                            <Typography variant="h5" color="blue-gray">
                                Growth
                            </Typography>
                        </figcaption>
                    </figure>
                    <video className="h-full w-full rounded-lg" controls autoPlay>
                        <source src="https://youtu.be/JWZZmBuN5Fs?si=ILdMI2k6wNlRHM-r" type="video/mp4" />
                        Your browser does not support the video tag.
                    </video>
                </div>
                <Checkbox
                    label={
                        <Typography
                            variant="small"
                            color="gray"
                            className="flex items-center font-normal"
                        >
                            I agree the
                            <a
                                href="#"
                                className="font-medium transition-colors hover:text-gray-900"
                            >
                                &nbsp;Terms and Conditions
                            </a>
                        </Typography>
                    }
                    containerProps={{ className: "-ml-2.5" }}
                />
                <Button className="mt-6" fullWidth onClick={submit}>
                    Done !
                </Button>
            </form>
        </Card>
    </div>
);
}