Chart.defaults.global.defaultFontColor = "#e6e9ef"; //change font colour for all charts

loadData();

async function loadData() {
    let data = {};
    try {
        const response = await fetch("report.json");
        data = await response.json();
    } catch (error) { //no server, no report.json, or not JSON: still show the error messages
        document.getElementById("load-error").textContent = "Could not load report.json. Run make export, then serve this folder (e.g. npx serve).";
    }

    const cpuData = await Object.keys(data).includes("cpu") ? data.cpu : {};
    const pciData = await Object.keys(data).includes("pci") ? data.pci : {};
    const usbData = await Object.keys(data).includes("usb") ? data.usb : {};
    const memData = await Object.keys(data).includes("memory") ? data.memory : {};


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
    let loads = Object.keys(cpuData).includes("loads") ? cpuData.loads : ["The loads could not be read."];
    const average = Object.keys(cpuData).includes("average") ? cpuData.average : "The average load could not be read.";

    baseInfo.innerHTML = model;
    
    //loads
    const cores = String(count).match(/\d+/); //"There are 16 cores" -> 16
    loadTable.innerHTML = "<caption>"+(cores ? cores[0]+" CPU Core(s)" : "Loads:")+"</caption>";
    loadTable.border = "1";

    if (loads.length <= 0) { //make sure error message shows up
        loads = ["The loads could not be read."]
    }

    loads.forEach(item => {
        const row = document.createElement("tr");

        const textCell = document.createElement("td");
        textCell.textContent = item.replace("Load for core ", "Core "); //shorter, so the tile stays small
        row.appendChild(textCell);

        if (item.endsWith("%")) {
            addBar(item, row);
        }
        
        loadTable.appendChild(row);
    });

    //add average
    const avgRow = document.createElement("tr");
    const avgCell = document.createElement("td");
    avgCell.textContent = average;
    avgRow.appendChild(avgCell);

    if (average.endsWith("%")) {
        addBar(average, avgRow);
    }
    loadTable.appendChild(avgRow);
}

function addBar(item, row) { //add a bar representing % to a table
    let percent = item.substring(item.length-7, item.length); //get the percent

    const barCell = document.createElement("td"); //make a div with the bar for the load
    const bar = document.createElement("div");
    bar.className = "bar";
    bar.style.width = percent;

    barCell.appendChild(bar); //add the bar to the cell
    row.appendChild(barCell); //add the cell to the row

}

function fillOutPCI(pciData) {
    const deviceTable = document.getElementById("pci-devices");
    const vendorTable = document.getElementById("pci-vendors");
    
    const vendorCountObj = Object.keys(pciData).includes("vendorCount") ? pciData.vendorCount : {
        "names": [],
        "counts": []
    };

    let devices = Object.keys(pciData).includes("devices") ? pciData.devices : ["The devices could not be read."];
    let alphaDevices = Object.keys(pciData).includes("alphaDevices") ? pciData.alphaDevices : ["The devices could not be read."];
    let busDevices = Object.keys(pciData).includes("busDevices") ? pciData.busDevices : ["The devices could not be read."];

    //make sure the tables all show the same number of devices
    if (devices.length <= 0 || alphaDevices.length <= 0 || busDevices.length <= 0 || devices.length != alphaDevices.length || devices.length != busDevices.length) { 
        devices = ["The devices could not be read."];
        alphaDevices = ["The devices could not be read."];
        busDevices = ["The devices could not be read."];
    }

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

    const barColours = [
        "gray",
        "green"
    ];

    const chart = document.getElementById("pci-vendors");
    if (vendorCountObj.names.length > 0 && vendorCountObj.counts.length > 0) {
        chart.innerHTML = "";

        new Chart(chart, {
            type: "bar",
            data: {
                labels: vendorCountObj.names,
                datasets: [{
                backgroundColor: barColours,
                data: vendorCountObj.counts
                }]
            },
            options: {
                title: {
                display: true,
                text: "Device Count by Vendor"
                },
                legend: {
                    display: false
                },
                scales: { //make the vendor count start at 0
                    xAxes: [{
                        gridLines: {
                            color: "white"
                        }
                    }],
                    yAxes: [{
                        display: true,
                        gridLines: {
                            color: "white"
                        },
                        ticks: {
                            beginAtZero: true
                        }
                    }]
                }
            }
        });
    } else { //display error message, hide chart
        document.getElementById("pci-vendors").style.display = "none";
        document.getElementById("pci-vendor-error").style.display = "flex";
    }

    // //make vendor table
    // vendorTable.innerHTML = "";
    // vendorTable.border = "1";

    // //Add headers
    // const vendorHeaders = ["Vendor", "Count"];
    // const vendorHeaderRow = document.createElement("tr");

    // vendorHeaders.forEach(text => {
    //     const th = document.createElement("th");
    //     th.textContent = text;
    //     vendorHeaderRow.appendChild(th);
    // });
    // vendorTable.appendChild(vendorHeaderRow);

    // // Add vendor rows
    // if (Object.keys(vendorCountObj).includes("names") && Object.keys(vendorCountObj).includes("counts") && vendorCountObj.names.length == vendorCountObj.counts.length) {
    //     for (let i=0; i<vendorCountObj.names.length; i++) {
    //         const row = document.createElement("tr");

    //         const nameCell = document.createElement("td");
    //         const countCell = document.createElement("td");

    //         nameCell.innerText = vendorCountObj.names[i];
    //         countCell.innerText = vendorCountObj.counts[i];

    //         row.appendChild(nameCell);
    //         row.appendChild(countCell);
    //         vendorTable.appendChild(row);
    //     }
    // } else {
    //     const row = document.createElement("tr");

    //     const nameCell = document.createElement("td");
    //     const countCell = document.createElement("td");

    //     nameCell.innerText = "Couldn't read vendors";
    //     countCell.innerText = "Couldn't read counts";

    //     row.appendChild(nameCell);
    //     row.appendChild(countCell);
    //     vendorTable.appendChild(row);
    // }
}

function fillOutUSB(usbData) {
    const deviceTable = document.getElementById("usb-devices");
    
    let devices = Object.keys(usbData).includes("devices") ? usbData.devices : ["The devices could not be read."];

    //make device table
    deviceTable.innerHTML = "<caption>USB Devices</caption>";
    deviceTable.border = "1";

    if (devices.length <= 0) { //make sure error msg shows
        devices = ["The devices could not be read."];
    }

    devices.forEach(device => {
        const row = document.createElement("tr");

        const cell = document.createElement("td");
        cell.innerText = device;

        row.appendChild(cell);
        deviceTable.appendChild(row);
    })
}

function fillOutMem(memData) {
    const used = Object.keys(memData).includes("used") ? memData.used : -1;
    const total = Object.keys(memData).includes("total") ? memData.total : -1;

    const percent = Object.keys(memData).includes("percent") ? memData.percent : "Memory couldn't be read";
    document.getElementById("memory-base").innerHTML = percent;

    const barColours = [
    "red",
    "green"
    ];

    const chart = document.getElementById("memChart");
    if (used >= 0 && total > used) {
        chart.innerHTML = "";

        new Chart(chart, {
            type: "pie",
            data: {
                labels: ["Used", "Unused"],
                datasets: [{
                backgroundColor: barColours,
                data: [used, total-used]
                }]
            },
            options: {
                title: {
                display: true,
                text: "Memory Usage (GiB)"
                }
            }
        });
    }
}