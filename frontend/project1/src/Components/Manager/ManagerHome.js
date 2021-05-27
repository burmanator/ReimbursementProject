import React, {useState} from 'react';
import RegisterNewEmployee from "./RegisterNewEmployee";
import ReviewPendingRequests from "./ReviewPendingRequests";
import ReviewPreviousReimbursements from "./ReviewPreviousReimbursements";
import ViewEmployees from "./ViewEmployees";
import {NotificationManager} from "react-notifications";
import UserProfile from "../Employee/UserProfile";
import axios from "axios";
function ManagerHome(props){
    const [nav, setNav] = useState("NA");
    const [email, setEmail] = useState("");

    React.useEffect(() => {
        //current Page
        getEmail();
        const currPage = localStorage.getItem("nav");
        setNav(currPage);
    }, [])

    React.useEffect(() => {
        // console.log(currentUser);
        localStorage.setItem("nav", nav )
    }, [nav])

    async function getEmail(){
        let username = props.username;
        console.log(username);
        await axios({
            method: 'get',
            url: "http://localhost:9999/getAdminEmail",
            params: {username},
            headers : {
                'Content-Type': 'application/json'
            }
        }).then(res => {
            if(res.status===200){
                setEmail(res.data);
                console.log(email);
            }
            else{
                alert("could not retrieve an email")
            }
        })
            .catch(err => console.log(err));
    }

    function registerNewEmployee() {
        setNav("registerNewEmployee")
    }

    function reviewPendingRequests() {
        setNav("reviewPendingRequests")
    }

    function viewPreviousReimbursements() {
        setNav("viewPreviousReimbursements")
    }

    function updateProfile() {
        setNav("userProfile")
    }

    function viewEmployees() {
        setNav("viewEmployees")
    }

    function Logout() {
        props.setLoggedIn(false);
        props.setUsername("");
        props.setPassword("");
        // NotificationManager.success('Successful', 'Successfully logged out!');
    }

    if(nav==="NA"){
        return(
            <div>
                <h3>Welcome to the Admin Homepage, {props.username}!</h3>
                <button className="link" onClick={registerNewEmployee}>Register New Employee</button>
                <button className="link" onClick={reviewPendingRequests}>Review Pending Requests</button>
                <button className="link" onClick={viewPreviousReimbursements}>View Previous Reimbursements</button>
                <button className="link" onClick={updateProfile} >User Profile</button>
                <button className="link" onClick={viewEmployees}>View Employees</button>
                <button className="link" onClick={Logout}>Log out</button>
            </div>
        )}
    else{
        if(nav==="registerNewEmployee"){
            // return <ViewPending setNav={setNav} username={props.username}/>
            return <RegisterNewEmployee setNav={setNav}/>
        }
        else if(nav==="reviewPendingRequests"){
            // return<NewReimbursement setNav={setNav} username={props.username}/>
            return <ReviewPendingRequests setNav={setNav} admin={props.username}/>

        }
        else if(nav==="viewPreviousReimbursements"){
            // return <UserProfile setNav={setNav} username={props.username}/>
            return <ReviewPreviousReimbursements setNav={setNav}/>
        }
        else if(nav==="userProfile"){
            return <UserProfile setNav={setNav} username={props.username} email={email}
                                setUsername={props.setUsername} setEmail={setEmail} isAdmin={true}/>
        }
        else if(nav==="viewEmployees"){
            // return <PrevReimbursement setNav={setNav} username={props.username}/>
            return <ViewEmployees setNav={setNav}/>
        }
    }
}

export default ManagerHome;