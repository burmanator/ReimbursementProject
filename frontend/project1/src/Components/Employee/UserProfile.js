import EmployeeHome from "./EmployeeHome";
import {BrowserRouter as Router, Switch, Route, Link} from 'react-router-dom';
import React, {useState} from 'react';
import ChangeUsername from "./ChangeUsername";
import ChangePassword from "./ChangePassword";
import ChangeEmail from "./ChangeEmail";

function UserProfile(props){
    function back() {
        props.setNav("NA")
    }

    return(
        <div>
            <button onClick={back}>X</button>
            <h5>Update User Profile</h5>
            <form className="user-profile">
                <ChangeUsername username={props.username} setUsername={props.setUsername} isAdmin={props.isAdmin}/>
                <ChangePassword username={props.username} isAdmin={props.isAdmin}/>
                <ChangeEmail username={props.username} email={props.email} setEmail={props.setEmail}
                             setUsername={props.setUsername} isAdmin={props.isAdmin}/>

            </form>

        </div>



    );
}

export default UserProfile;