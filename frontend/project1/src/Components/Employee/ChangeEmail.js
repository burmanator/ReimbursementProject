import React, {useState} from "react";
import axios from "axios";
import {NotificationContainer, NotificationManager} from "react-notifications"
import 'react-notifications/lib/notifications.css';

function ChangeEmail(props) {
    const [newEmail, setNewEmail] = useState("");
    let currEmail= props.email;

    async function update(){
        let username = props.username;
        if(newEmail==null){
            return;
        }
        let path = "http://localhost:9999/updateEmail";
        if(props.isAdmin){
            path = "http://localhost:9999/updateAdminEmail";
        }
        await axios({
            method: 'post',
            url: path,
            params: {username, currEmail, newEmail},
            headers : {
                'Content-Type': 'application/json'
            }
        }).then(res => {
            let status = res.data;
            if(status===200){
                props.setEmail(newEmail);
                NotificationManager.success('Successful', 'Successfully Changed Email!');
            }
            else{
                NotificationManager.error('Unsuccessful', 'Sorry, we couldn\'t change your email');
            }
        })
            .catch(err => console.log(err));
    }

    return(
        <div>
            <NotificationContainer/>
            <form className="update-form" >
                <h6>Change Email</h6>
                <label> Current email: {currEmail}</label>
                <input type="text" onChange={e=>{setNewEmail(e.target.value)}} placeholder="enter new email "/>
                <button type="submit" onClick={e=>{e.preventDefault(); update()}}>Update</button>
            </form>

        </div>
    );
}
export default ChangeEmail;