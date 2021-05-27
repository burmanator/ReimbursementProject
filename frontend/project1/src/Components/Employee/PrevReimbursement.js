import React, {useState} from "react";
import axios from "axios";

function PrevReimbursement(props){
    const [data, setData] = useState([]);

    function back() {
        props.setNav("NA")
    }

    React.useEffect(()=>{
        query().then(r => console.log(r));
    },[]);

    async function query(){
        let password ="";
        let username = props.username;
        await axios({
            method: 'get',
            url: "http://localhost:9999/prevReimbursements",
            params: {username, password},
            headers : {
                'Content-Type': 'application/json'
            }
        }).then(res=>{
            if(res.status ===200){
                console.log(res.data[0]);
                setData(res.data);
            }
            else{
                alert("something went wrong");
            }
        })
    }
    function getFormattedDate(date) {
        let year = date.getFullYear();

        let month = (1 + date.getMonth()).toString();
        month = month.length > 1 ? month : '0' + month;

        let day = date.getDate().toString();
        day = day.length > 1 ? day : '0' + day;

        return  year+ '-' + month + '-' +day ;
    }



    return(

        <div>
            <button className="close-button" onClick={back}>X</button>
            <h5>View Previous Reimbursements</h5>
            <table>
            <div className="scroll">
                <tr>
                    <th>Title</th>
                    <th>Amount</th>
                    <th>Date</th>
                    <th>Approval</th>
                    <th>Deciding Manager</th>

                </tr>
                {data.map(d=>{
                    return (
                            <tr className="view-pending" key={d.id}>
                                <td> {d.title}</td>
                                <td>{d.amount}</td>,
                                <td> {getFormattedDate(new Date(d.date))} </td>
                                <td> {d.approved}</td>
                                <td> {d.approvedBy}</td>
                            </tr>
                    )
                })
                }
            </div>
            </table>
        </div>


    );
}

export default PrevReimbursement;