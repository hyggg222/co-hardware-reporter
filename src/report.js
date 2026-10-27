async function loadData() {
    const response = await fetch("../report.json");
    const data = await response.json();
    
    const cpuData = await Object.keys(data).includes("cpu") ? data.cpu : {};

    fillOutCPU(cpuData);
}

function fillOutCPU(cpuData) {
    const loadTable = document.getElementById("cpu-loads");
    const baseInfo = document.getElementById("cpu-baseInfo");

    const model = Object.keys(cpuData).includes("model") ? cpuData.model : "The model could not be read.";
    const count = Object.keys(cpuData).includes("count") ? cpuData.count : "The number of cores could not be read.";
    const loads = Object.keys(cpuData).includes("loads") ? cpuData.loads : ["The loads could not be read."];
    const average = Object.keys(cpuData).includes("average") ? cpuData.average : "The average load could not be read.";

    baseInfo.innerHTML = "CPU Info: <br/>"+count+" "+model;
    //plus model, number of cores
    
    //loads
    loadTable.innerHTML = "<caption>CPU Loads:</caption>";
    loadTable.border = "1";

    loads.forEach(item => {
        const row = document.createElement("tr");

        const cell = document.createElement("td");
        cell.textContent = item;
        row.appendChild(cell);
        
        loadTable.appendChild(row);
    });
    //for real one, have headers Core, Loads
    loadTable.innerHTML += "<td>"+average+"</td></tr>"; //add average, end the last row
}

loadData();