loadData();

async function loadData() {
    const response = await fetch("../report.json");
    const data = await response.json();
    
    const cpuData = await Object.keys(data).includes("cpu") ? data.cpu : {};
    const pciData = await Object.keys(data).includes("pci") ? data.pci : {};
    const usbData = await Object.keys(data).includes("usb") ? data.usb : {};
    const memData = await Object.keys(data).includes("memory") ? data.memory : "Memory: Couldn't read memory";


    fillOutCPU(cpuData);
    fillOutPCI(pciData);
    fillOutUSB(usbData);
    fillOutMem(memData);
}

function fillOutCPU(cpuData) {
    const loadTable = document.getElementById("cpu-loads");
    const baseInfo = document.getElementById("cpu-base");

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

function fillOutPCI(pciData) {
    const base = document.getElementById("pci-base");
    const deviceTable = document.getElementById("pci-devices");
    const vendorTable = document.getElementById("pci-vendors");
    
    const count = Object.keys(pciData).includes("count") ? pciData.count : "The number of PCI devices could not be read.";
    const vendorCountObj = Object.keys(pciData).includes("vendorCount") ? pciData.vendorCount : {
        "names": ["The names could not be read"],
        "counts": ["The counts could not be read"]
    };

    let devices = Object.keys(pciData).includes("devices") ? pciData.devices : ["The devices could not be read."];
    let alphaDevices = Object.keys(pciData).includes("alphaDevices") ? pciData.alphaDevices : ["The devices could not be read."];
    let busDevices = Object.keys(pciData).includes("busDevices") ? pciData.busDevices : ["The devices could not be read."];

    if (devices.length != alphaDevices.length || devices.length != busDevices.length) { //make sure the devices all show the same number of devices
        devices = ["The devices could not be read."];
        alphaDevices = ["The devices could not be read."];
        busDevices = ["The devices could not be read."];
    }

    base.innerHTML = "PCI Devices: <br/>";
    base.innerHTML += count;
    
    //make device table
    deviceTable.innerHTML = "";
    deviceTable.border = "1";

    allDevices = [devices, alphaDevices, busDevices];

    //Add headers
    const headers = ["Devices", "Devices Alphabetically", "Devices by Bus"];
    const headerRow = document.createElement("tr");

    headers.forEach(text => {
        const th = document.createElement("th");
        th.textContent = text;
        headerRow.appendChild(th);
    });
    deviceTable.appendChild(headerRow);

    // Add device rows
    //3 columns, a row for each device
    for (let i=0; i<devices.length; i++) {
        const row = document.createElement("tr");

        allDevices.forEach(item => {
            const cell = document.createElement("td");
            cell.textContent = item[i];
            row.appendChild(cell);
        })

        deviceTable.appendChild(row);
    }

    //make vendor table
    vendorTable.innerHTML = "<caption>PCI Vendors:</caption>";
    vendorTable.border = "1";

        //Add headers
        const vendorHeaders = ["Vendor", "Count"];
        const vendorHeaderRow = document.createElement("tr");

        vendorHeaders.forEach(text => {
            const th = document.createElement("th");
            th.textContent = text;
            vendorHeaderRow.appendChild(th);
        });
        vendorTable.appendChild(vendorHeaderRow);

        // Add device rows
        if (Object.keys(vendorCountObj).includes("names") && Object.keys(vendorCountObj).includes("counts") && vendorCountObj.names.length == vendorCountObj.counts.length) {
            for (let i=0; i<vendorCountObj.names.length; i++) {
                const row = document.createElement("tr");

                const nameCell = document.createElement("td");
                const countCell = document.createElement("td");

                nameCell.innerText = vendorCountObj.names[i];
                countCell.innerText = vendorCountObj.counts[i];

                row.appendChild(nameCell);
                row.appendChild(countCell);
                vendorTable.appendChild(row);
            }
        } else {
            const row = document.createElement("tr");

            const nameCell = document.createElement("td");
            const countCell = document.createElement("td");

            nameCell.innerText = "Couldn't read vendors";
            countCell.innerText = "Couldn't read counts";

            row.appendChild(nameCell);
            row.appendChild(countCell);
            vendorTable.appendChild(row);
        }
}

function fillOutUSB(usbData) {
    const base = document.getElementById("usb-base");
    const deviceTable = document.getElementById("usb-devices");
    
    const count = Object.keys(usbData).includes("count") ? usbData.count : "The number of USB devices could not be read.";

    let devices = Object.keys(usbData).includes("devices") ? usbData.devices : ["The devices could not be read."];

    base.innerHTML = "USB Devices: <br/>";
    base.innerHTML += count;
    
    //make device table
    deviceTable.innerHTML = "<caption>USB Devices</caption>";
    deviceTable.border = "1";

    devices.forEach(device => {
        const row = document.createElement("tr");

        const cell = document.createElement("td");
        cell.innerText = device;

        row.appendChild(cell);
        deviceTable.appendChild(row);
    })
}

function fillOutMem(memData) {
    document.getElementById("memory").innerHTML = memData;
}