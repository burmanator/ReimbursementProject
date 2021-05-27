import axios from "axios";
import React, { useState } from 'react';
import EmployeeHome from "./EmployeeHome";
import 'react-notifications/lib/notifications.css';
import {NotificationContainer, NotificationManager} from 'react-notifications';

//Login Page

function Login(props) {
    const [currentUser, setCurrentUser] = useState({username:'', password:''});
    const [loggedIn, setLoggedIn] = useState(false);
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [nav, setNav] = useState("NA");
    const [currPage, setCurrPage] = useState("Login");

    function back() {
        props.setPortal("NA")
    }

    React.useEffect(() => {
        //currUser
        const username1 = localStorage.getItem("username")
        setUsername("")
        if(username1!==""){
            setUsername(username1)
        }

        //logged in status
        const loggedStatus = (localStorage.getItem("loggedIn")=== 'true');
        setLoggedIn(loggedStatus)

        //current Page
        const currPage = localStorage.getItem("currPage");
        setCurrPage(currPage);
        setNav(currPage);
    }, [])

    React.useEffect(() => {
        // console.log(currentUser);
        localStorage.setItem("username", username)
        localStorage.setItem("loggedIn",loggedIn.toString())
        localStorage.setItem("currPage", currPage)
    }, [loggedIn, currentUser])


    const handleUsername = (event) => {
        event.preventDefault();
        const username = event.target.value;
        setUsername(username);
    }
    const handlePassword = (event) => {
        event.preventDefault();
        const password = event.target.value;
        setPassword(password);
    }
    async function handleSubmit(e) {
        e.preventDefault();
        let email="";
        await axios({
            method: 'post',
            url: "http://localhost:9999/login",
            data: {username, password, email},
            headers : {
                'Content-Type': 'application/json'
            }
        }).then(res => {
            let result = res.data;
                if(result===200){
                    setLoggedIn(true);
                    NotificationManager.success('Welcome', 'Successfully Logged In!');
                }
                else{
                    setLoggedIn(false);
                    NotificationManager.error('Unsuccessful Login', 'Sorry, we couldn\'t validate your account');
                }
            })
            .catch(err => console.log(err));
    }

    if(loggedIn === true){
        return(
            <div>
                <NotificationContainer/>
                <EmployeeHome username={username} setUsername={setUsername} setPassword={setPassword}
                              setLoggedIn={setLoggedIn} currPage={currPage}
                              setNav={setNav} nav={nav} setUsername={setUsername}/>
            </div>

        );
    }
    else{
        return (
            <div>
                <NotificationContainer/>
                <button onClick={back}>X</button>
                <h3>Welcome to the Employee Portal</h3>
                <form className="Login" >
                    <label>Username</label>
                    <input type="text" placeholder="username" onChange={e => handleUsername(e)}/>
                    <label>Password</label>
                    <input type="password" placeholder="password"  onChange={e => handlePassword(e)}/>
                    <button type="submit" onClick={handleSubmit}>Submit</button>
                </form>
            </div>
        );

    }


}

export default Login;
