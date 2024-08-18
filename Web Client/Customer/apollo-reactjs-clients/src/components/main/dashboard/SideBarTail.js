import {
    Card,
    Typography,
    List,
    ListItem,
    ListItemPrefix,
    ListItemSuffix,
    Chip,
    Accordion,
    AccordionHeader,
    AccordionBody,
    Alert
} from "@material-tailwind/react";
import {
    PresentationChartBarIcon,
    ShoppingBagIcon,
    UserCircleIcon,
    Cog6ToothIcon,
    InboxIcon,
    PowerIcon,
    BellIcon,
    PuzzlePieceIcon
} from "@heroicons/react/24/solid";
import {
    ChevronRightIcon,
    ChevronDownIcon,
    CubeTransparentIcon,

} from "@heroicons/react/24/outline";
import { Link } from "react-router-dom";
import React from "react";

export function SideBarTail() {
    const [open, setOpen] = React.useState(0);
    const [openAlert, setOpenAlert] = React.useState(true);

    const handleOpen = (value) => {
        setOpen(open === value ? 0 : value);
    };
    return (
        <div>
            <Card className="h-[calc(100vh-2rem)] w-full max-w-[20rem] p-4 shadow-xl shadow-blue-gray-900/5">
                <div className="mb-2 p-4 bg-gray-700 text-white w-full mr-30 border-2 rounded-xl">
                    <Typography variant="h5" color="blue-gray">
                        Apollo
                    </Typography>
                </div>
                <List>
                    <Link to="/dashboard" className="text-black hover:text-blue-400 font-bold text-bodyFont">
                    <ListItem>
                        <ListItemPrefix>
                            <PresentationChartBarIcon className="h-5 w-5" />
                        </ListItemPrefix>
                            Dashboard
                    </ListItem>
                    </Link>

                    <Accordion
                        open={open === 1}
                        icon={
                            <ChevronDownIcon
                                strokeWidth={2.5}
                                className={`mx-auto h-4 w-4 transition-transform ${open === 1 ? "rotate-180" : ""}`}
                            />
                        }
                    >
                        <ListItem className="p-0" selected={open === 1}>
                            <AccordionHeader onClick={() => handleOpen(1)} className="border-b-0 p-3">
                                <ListItemPrefix>
                                    <BellIcon className="h-5 w-5" />
                                </ListItemPrefix>
                                <Typography color="blue-gray" className="mr-auto font-normal">
                                    Categories
                                </Typography>
                            </AccordionHeader>
                        </ListItem>
                        <AccordionBody className="py-1">
                            <List className="p-0">
                                <Link to="/dashboard/categories" className="text-black hover:text-blue-400 font-bold text-bodyFont">
                                <ListItem>
                                    <ListItemPrefix>
                                        <ChevronRightIcon strokeWidth={3} className="h-3 w-5" />
                                    </ListItemPrefix>
                                        Category List
                                </ListItem>
                                </Link>
                                <Link to="/dashboard/categories" className="text-black hover:text-blue-400 font-bold text-bodyFont">
                                <ListItem>
                                    <ListItemPrefix>
                                        <ChevronRightIcon strokeWidth={3} className="h-3 w-5" />
                                    </ListItemPrefix>
                                        Category
                                </ListItem>
                                </Link>

                                <ListItem>
                                    <ListItemPrefix>
                                        <ChevronRightIcon strokeWidth={3} className="h-3 w-5" />
                                    </ListItemPrefix>
                                    Projects
                                </ListItem>
                            </List>
                        </AccordionBody>
                    </Accordion>
                    <Accordion
                        open={open === 2}
                        icon={
                            <ChevronDownIcon
                                strokeWidth={2.5}
                                className={`mx-auto h-4 w-4 transition-transform ${open === 2 ? "rotate-180" : ""}`}
                            />
                        }
                    >
                        <ListItem className="p-0" selected={open === 2}>
                            <AccordionHeader onClick={() => handleOpen(2)} className="border-b-0 p-3">
                                <ListItemPrefix>
                                    <ShoppingBagIcon className="h-5 w-5" />
                                </ListItemPrefix>
                                <Typography color="blue-gray" className="mr-auto font-normal">
                                    Inventory
                                </Typography>
                            </AccordionHeader>
                        </ListItem>
                        <AccordionBody className="py-1">
                            <List className="p-0">
                                <Link to="/dashboard/products" className="text-black hover:text-blue-400 font-bold text-bodyFont">
                                <ListItem>
                                    <ListItemPrefix>
                                        <ChevronRightIcon strokeWidth={3} className="h-3 w-5" />
                                    </ListItemPrefix>
                                        Product List
                                </ListItem>
                                </Link>
                                <Link to="/dashboard/products" className="text-black hover:text-blue-400 font-bold text-bodyFont">
                                <ListItem>
                                    <ListItemPrefix>
                                        <ChevronRightIcon strokeWidth={3} className="h-3 w-5" />
                                    </ListItemPrefix>
                                        Product
                                </ListItem>
                                </Link>

                            </List>
                        </AccordionBody>
                    </Accordion>
                    <ListItem>
                        <ListItemPrefix>
                            <UserCircleIcon className="h-5 w-5" />
                        </ListItemPrefix>
                        <Link to="/dashboard/orders" className="text-black hover:text-blue-400 font-bold text-bodyFont">
                            Orders
                        </Link>
                    </ListItem>
                    <ListItem>
                        <ListItemPrefix>
                            <PuzzlePieceIcon className="h-5 w-5" />
                        </ListItemPrefix>
                        <Link to="/dashboard/reviews" className="text-black hover:text-blue-400 font-bold text-bodyFont">
                            Reviews
                        </Link>
                    </ListItem>
                    <hr className="my-2 border-blue-gray-50" />
                    <ListItem>
                        <ListItemPrefix>
                            <InboxIcon className="h-5 w-5" />
                        </ListItemPrefix>
                        Inbox
                        <ListItemSuffix>
                            <Chip value="14" size="sm" variant="ghost" color="blue-gray" className="rounded-full" />
                        </ListItemSuffix>
                    </ListItem>
                    <Link to="/dashboard/profile" className="text-black hover:text-blue-400 font-bold text-bodyFont">
                    <ListItem>
                        <ListItemPrefix>
                            <UserCircleIcon className="h-5 w-5" />
                        </ListItemPrefix>
                        Profile
                    </ListItem>
                    </Link>
                    <ListItem>
                        <ListItemPrefix>
                            <Cog6ToothIcon className="h-5 w-5" />
                        </ListItemPrefix>
                        Settings
                    </ListItem>
                    <ListItem>
                        <ListItemPrefix>
                            <PowerIcon className="h-5 w-5" />
                        </ListItemPrefix>
                        Log Out
                    </ListItem>
                </List>
                <Alert open={openAlert} className="mt-auto" onClose={() => setOpenAlert(false)}>
                    <CubeTransparentIcon className="mb-4 h-12 w-12" />
                    <Typography variant="h6" className="mb-1">
                        Upgrade to PRO
                    </Typography>
                    <Typography variant="small" className="font-normal opacity-80">
                        Upgrade to Apollo Account PRO and get even more discounts, deals, advanced features
                        and premium.
                    </Typography>
                    <div className="mt-4 flex gap-3">
                        <Typography
                            as="a"
                            href="#"
                            variant="small"
                            className="font-medium opacity-80"
                            onClick={() => setOpenAlert(false)}
                        >
                            Dismiss
                        </Typography>
                        <Typography as="a" href="#" variant="small" className="font-medium">
                            Upgrade Now
                        </Typography>
                    </div>
                </Alert>
            </Card>
        </div>
    );
}