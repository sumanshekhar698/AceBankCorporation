// Input fields
const customerId = document.getElementById("cutomer_id");
const password = document.getElementById("password");

// Form
const form = document.getElementById("login-form");

// Validate form

form.addEventListener("submit", function(event) {
  if (validateCustomerID() && validatePassword()) {
    // alert("submit?");
    // $(form)
    //   .unbind("submit")
    //   .submit();
    console.log("submit");
  } else {
    // Prevent default behaviour
    event.preventDefault();
  }
});

// *****************************************************
// Validators

function validateCustomerID() {
  if (checkIfEmpty(customerId)) return;
  if (!isCustomerID(customerId, 6)) return;
  return true;
}

function validatePassword() {
  if (checkIfEmpty(password)) return;
  if (!isPasswordLength(password, 10)) return;
  return true;
}

//  ****************************************************
//   Util functions

function isCustomerID(customerId) {
  if (customerId.value.length == 6) {
    setValid(customerId);
    return true;
  } else {
    setInvalid(customerId, "Mistake in Customer ID");
    return false;
  }
}

function isPasswordLength(password, length) {
  if (password.value.length >= length) {
    setValid(password);
    return true;
  } else {
    setInvalid(password, "Password is too short");
    return false;
  }
}

// *****************************************************
function checkIfEmpty(field) {
  if (isEmpty(field.value.trim())) {
    // Set field invalid
    setInvalid(field, "*Mandatory");
    return true;
  }
  // Set field valid if it isn't empty
  setValid(field);
  return false;
}

function isEmpty(value) {
  if (value === "") return true; //return true if empty
  return false;
}

function setInvalid(field, message) {
  // field.className = "Invalid";
  const formControl = field.parentElement;
  const small = formControl.querySelector("small");
  formControl.className = "form-control error";
  small.innerText = message;
}

function setValid(field) {
  const formControl = field.parentElement;
  formControl.className = "form-control success";
}
