import React, {useState} from "react";
import axios from "axios";
import {NotificationManager} from "react-notifications";

function ChangeUsername(props){


    let newUsername;

    async function update(e) {
        e.preventDefault();
        console.log(newUsername);
        let currUsername =props.username;
        if((newUsername==null)|| (newUsername==undefined)){return}

        let path = "http://localhost:9999/updateUsername";

        if(props.isAdmin){
            path = "http://localhost:9999/updateAdminUsername"
        }

        console.log(path);
        console.log(newUsername);
        await axios({
            method: 'post',
            url: path,
            params: {currUsername, newUsername},
            headers : {
                'Content-Type': 'application/json'
            }
        }).then(res => {
            let status = res.data;
            if(status===200){
                props.setUsername(newUsername);
                NotificationManager.success('Successful', 'Successfully Changed Username!');
            }
            else{
                NotificationManager.error('Unsuccessful', 'Sorry, we couldn\'t change your username');
            }
        })
            .catch(err => console.log(err));
    }

    return(
    <div>
        <form className="update-form" onSubmit={update}>
            <h6>Change Username</h6>
            <label> Current Username: {props.username}</label>
            <input type="text" onChange={e=>newUsername= (e.target.value)} placeholder="enter new username "/>
            <button onClick={update}>Update</button>
        </form>
    </div>
);
}
export default ChangeUsername;