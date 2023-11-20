// Placeholder for WebSocket handling script
// This will be filled with the functionality to connect to the server and send/receive messages
console.log("Chat script loaded");

function updateSliderValue(sliderId, displayId) {
    const slider = document.getElementById(sliderId);
    document.getElementById(displayId).textContent = slider.value;
}

function toggleContainerInputs(checkboxElement, containerId) {
    const container = document.getElementById(containerId);
    const inputs = container.querySelectorAll('input, textarea, select, button');
    const shouldBeEnabled = checkboxElement.checked;
    container.classList.toggle('d-none', !shouldBeEnabled);
    inputs.forEach(input => {
        input.disabled = !shouldBeEnabled;
    });
}

function autoGrow(element, maxHeight) {
    const minHeight = 75; // Assuming a row height of 20px
    element.style.height = "5px"; // Temporarily shrink to get the correct scrollHeight
    const newHeight = Math.min(element.scrollHeight, maxHeight);
    element.style.height = (newHeight > minHeight ? newHeight : minHeight) + "px";
    // Add scrollbar if the content is larger than maxHeight
    element.style.overflowY = newHeight >= maxHeight ? "scroll" : "hidden";
}

function formatJson(textarea) {
    try {
        let json = JSON.parse(textarea.value);
        textarea.value = JSON.stringify(json, null, 2);
    } catch (e) {
        console.error("The text is not valid JSON.");
    }
}

function initializeTooltips() {
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
}

function isThisWeek(date) {
    const now = new Date();
    const startOfWeek = new Date(now.getFullYear(), now.getMonth(), now.getDate() - now.getDay());
    const endOfWeek = new Date(startOfWeek.getFullYear(), startOfWeek.getMonth(), startOfWeek.getDate() + 7);
    return date >= startOfWeek && date < endOfWeek;
}

function displayTime(timestamp) {
    const now = new Date()
    const timestampDate = new Date(timestamp);
    if (isThisWeek(timestampDate) && timestampDate < now) {
        // Relative time format for current week timestamps
        const diff = now - timestampDate;
        if (diff < 3600000) { // less than 1 hour
            const minutes = Math.round(diff / 60000);
            return minutes + ' minutes ago';
        } else if (diff < 86400000) { // less than 24 hours
            const hours = Math.round(diff / 3600000);
            return hours + ' hours ago';
        } else {
            // Show the weekday for dates within the current week
            const options = {weekday: 'long'};
            return 'on ' + timestampDate.toLocaleDateString(undefined, options);
        }
    } else {
        // Localized format for dates not in the current week
        const options = {
            year: 'numeric',
            month: 'numeric',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit',
            hour12: false
        };
        return timestampDate.toLocaleString(undefined, options);
    }
}

//I'd think temporary until I get the real value from the DB
function formatTimestamps() {
    // Find all elements with a specific class
    const timestamps = document.querySelectorAll('[data-format-timestamp="true"]');
    for (let i = 0; i < timestamps.length; i++) {
        const timestamp = timestamps[i].textContent.trim().replace(/"/g, '');
        timestamps[i].textContent = displayTime(timestamp);
    }
}

window.onload = formatTimestamps;