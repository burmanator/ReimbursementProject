import React, {useState} from 'react';
import axios from "axios";
import emailjs from 'emailjs-com';
import {NotificationManager} from "react-notifications";


function RegisterNewEmployee(props){
    const [newUsername, setNewUsername] = useState("");
    const [employeeStatus, setEmployeeStatus] = useState("");
    const [email, setEmail] = useState("");
    let generator = require('generate-password');

    function back() {
        props.setNav("NA")
    }

    function sendEmail(username, password, email){

        let company = "Golden Gates Bank";
        let messageBody = "Your temporary username is : " + username+" and your temporary password is "+ password+
            ". Please log into your account at www.goldengatesbank.com";
        let message={
            "to_name": username,
            "from_name": company,
            "to_email": email,
            "message": messageBody
        }
        // console.log(message);
        emailjs.send('service_x6l75f7','template_ulr88ue', message,'user_8lya55qZw4lyu8St4iZ74').then((result)=>{
        })
    }

    async function createNewUser(e){
        e.preventDefault();

        let username = newUsername
        let password = generator.generate({
            length: 10,
            numbers: true
        });

        if(employeeStatus==="admin"){
            //post request
            await axios({
                method: 'post',
                url: "http://localhost:9999/newAdmin",
                data: {username, email, password},
                headers : {
                    'Content-Type': 'application/json'
                }
            }).then(res => {
                let status = res.data;
                if(status===200){
                    sendEmail(username, password, email);
                    NotificationManager.success('Successful', 'New Admin Account Created!');
                }
                else{
                    NotificationManager.error('Unsuccessful', 'Sorry, we couldn\'t change your email');
                }
            })
                .catch(err => console.log(err));
        }
        else{
            //post request
            await axios({
                method: 'post',
                url: "http://localhost:9999/newEmployee",
                data: {username, email, password},
                headers : {
                    'Content-Type': 'application/json'
                }
            }).then(res => {
                let status = res.data;
                if(status===200){
                    sendEmail(username, password, email);
                    NotificationManager.success('Successful', 'New Employee Account Created!');
                }
                else{
                    NotificationManager.error('Unsuccessful', 'Sorry, we couldn\'t change your email');
                }
            })
                .catch(err => console.log(err));
        }
    }

    return (
        <div>
            <button onClick={back}>X</button>
            <h5>Register New User</h5>
            <form onSubmit={createNewUser}>
                <label>username</label>
                <input type="text" onChange={e=>{setNewUsername(e.target.value)}}/>
                <label>email</label>
                <input type="email" onChange={e=>{setEmail(e.target.value)}}/>
                <select onChange={e=>{setEmployeeStatus(e.target.value)}}>
                    <option value="employee">Employee</option>
                    <option value="admin">Admin</option>
                </select>
                <button type="submit">Create User</button>
            </form>

        </div>
    );
}
export default RegisterNewEmployee;