import UserProfile from './UserProfile';
import React, {useState} from "react";
import NewReimbursement from "./NewReimbursement";
import ViewPending from "./ViewPending";
import axios from "axios";
import PrevReimbursement from "./PrevReimbursement";
import {NotificationManager} from "react-notifications";

function EmployeeHome(props) {
    // const [username, setUsername] = useState({username:props.username, password:"", reimbursements: "", firstname: "", lastname:""});
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

    function Logout(){
        props.setLoggedIn(false);
        props.setUsername("");
        props.setPassword("");
        // NotificationManager.success('Successful', 'Successfully logged out!');
    }

    async function getEmail(){
        let username = props.username;
        await axios({
            method: 'get',
            url: "http://localhost:9999/getEmail",
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

    function NavPendingRequest() {
        setNav("Pending")
    }

    function NavNewReimbursement() {
        setNav("NewReimbursement")
    }

    function NavViewReimbursement() {
        setNav("PrevReimbursement")
    }


    function NavUserProfile() {
        setNav("UserProfile")
    }

    if(nav==="NA"){
        return(
        <div>
            <h3>Welcome to the Employee Homepage, {props.username}!</h3>
            <button className="link" onClick={NavPendingRequest}>Pending Requests</button>
            <button className="link" onClick={NavNewReimbursement}>New Reimbursement</button>
            <button className="link" onClick={NavViewReimbursement}>View Previous Reimbursement Requests</button>
            <button className="link" onClick={NavUserProfile} >User Profile</button>
            <button className="link" onClick={Logout}>Log out</button>
        </div>
        )}
    else{
        if(nav==="Pending"){
            return <ViewPending setNav={setNav} username={props.username}/>
        }
        else if(nav==="NewReimbursement"){
            return<NewReimbursement setNav={setNav} username={props.username}/>

        }
        else if(nav==="UserProfile"){
            return <UserProfile setNav={setNav} username={props.username} email={email}
                                setUsername={props.setUsername} setEmail={setEmail} isAdmin={false}/>
        }
        else if(nav==="PrevReimbursement"){
            return <PrevReimbursement setNav={setNav} username={props.username}/>
        }
    }

}

export default EmployeeHome;