import React,{useState} from "react";
import axios from "axios";
import {NotificationContainer, NotificationManager} from "react-notifications";
import 'react-notifications/lib/notifications.css';

function ChangePassword(props){
    const [password, setPassword]= useState("");
    const [newPassword1, setNewPassword1] = useState("");
    const [newPassword2, setNewPassword2] = useState("");

    async function update(e){
        //make a post request to the back end and try to match the passwords
        if(newPassword1!==newPassword2){
            NotificationManager.error('Failed', 'The new passwords you entered to not match');
        }
        let path = "http://localhost:9999/updatePassword";
        if(props.isAdmin){
            path ="http://localhost:9999/updateAdminPassword";
        }
        console.log(path);
        let username = props.username;
        console.log(username);
        console.log(newPassword1);
        await axios({
            method: 'post',
            url: path,
            params: {username,newPassword1, password},
            headers : {
                'Content-Type': 'application/json'
            }
        }).then(res => {
            let result = res.data;
            if(result===200){
                NotificationManager.success('Success', 'Successfully changed your password!');
            }
            else{
                NotificationManager.error('Failed', 'Sorry, we couldn\'t change your password');
            }
        })
            .catch(err => console.log(err));

    }

    return(
        <div>
            <NotificationContainer/>
            <form className="update-form">
                <h6>Change Password</h6>
                <input type="password" placeholder="enter current password" onChange={e=>setPassword(e.target.value)}/>
                <input type="password" placeholder="enter new password" onChange={e=>setNewPassword1(e.target.value)}/>
                <input type="password"  placeholder="enter new password" onChange={e=>setNewPassword2(e.target.value)}/>
                <button type="submit" onClick={e=>{e.preventDefault(); update()}}>Update</button>
            </form>
        </div>
    );
}
export default ChangePassword;