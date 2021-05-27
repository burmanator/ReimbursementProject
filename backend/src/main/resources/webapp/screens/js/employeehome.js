const welcome = document.getElementById("welcome");
console.log(welcome);
const h2 = document.createElement("h2");

welcome.appendChild(h2);

const promiseOfPendingReimbursement = fetch("http://localhost:8080/PendingReimbursement").then(r=>r.json()).then(data => {
    h2.innerText ="brhhhhh";
    welcome.appendChild(h2);
    return data;
});
window.onload = async () => {
    let someData = await promiseOfPendingReimbursement;
    console.log("onload");
};