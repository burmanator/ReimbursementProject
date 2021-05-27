import React, {useState} from 'react';
import axios from "axios";
import ManagerHome from "./ManagerHome"
import 'react-notifications/lib/notifications.css';
import {NotificationContainer, NotificationManager} from 'react-notifications';

function ManagerLogin(props){
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [loggedIn, setLoggedIn] = useState(false);
    const [nav, setNav] = useState("NA");
    const [currPage, setCurrPage] = useState("ManagerLogin");

    function back() {
        props.setPortal("NA")
    }

    React.useEffect(() => {
        //currUser
        const username1 = localStorage.getItem("adminUsername")
        setUsername("")
        if(username1!==""){
            setUsername(username1)
        }

        //logged in status
        const loggedStatus = (localStorage.getItem("managerLoggedIn")=== 'true');
        setLoggedIn(loggedStatus)

        //current Page
        const currPage = localStorage.getItem("adminCurrPage");
        setCurrPage(currPage);
        setNav(currPage);
    }, [])

    React.useEffect(() => {
        // console.log(currentUser);
        localStorage.setItem("adminUsername", username)
        localStorage.setItem("managerLoggedIn",loggedIn.toString())
        localStorage.setItem("adminCurrPage", currPage)
    }, [loggedIn])


    async function login(e){
        e.preventDefault();
        let email = "";
        //post request
        await axios({
            method: 'post',
            url: "http://localhost:9999/managerLogin",
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

    if(loggedIn===true){
        return (
            <div>
                <NotificationContainer/>
                <ManagerHome username={username} setLoggedIn={setLoggedIn}
                             setPassword={setPassword} setUsername={setUsername} nav={nav} setNav={setNav}/>
            </div>

        )
    }
    else{
        return(
            <div>
                <NotificationContainer/>
                <button onClick={back}>X</button>
                <h3>Welcome to the Admin Portal</h3>
                <form onSubmit={login}>
                    <label >username</label>
                    <input type="text" placeholder="username" onChange={e=>setUsername(e.target.value)}/>
                    <label >password</label>
                    <input type="password" placeholder="password" onChange={e=>setPassword(e.target.value)}/>
                    <button type="submit">Login</button>
                </form>
            </div>

        );
    }
}

export default ManagerLogin;