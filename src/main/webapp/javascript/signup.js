// Input fields
const firstName = document.getElementById("first_name");
const lastName = document.getElementById("last_name");
const aadharNumber = document.getElementById("aadhar_no");
const email = document.getElementById("email");
const password = document.getElementById("password");

// Form
const form = document.getElementById("signup-form");

// ******************************************************
// Validate Form

form.addEventListener("submit", function(event) {
  if (
    validateFirstName() &&
    validateLastName() &&
    validateAadhar() &&
    validateEmail() &&
    validatePassword()
  ) {
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

// *******************************************************
// Validators
function validateFirstName() {
  if (checkIfEmpty(firstName)) return;

  if (!checkIfOnlyLetters(firstName)) return;
  return true;
}

function validateLastName() {
  if (checkIfEmpty(lastName)) return;

  if (!checkIfOnlyLetters(lastName)) return;
  return true;
}

function validateAadhar() {
  if (checkIfEmpty(aadharNumber)) return;

  if (!isAadhar(aadharNumber)) return;
  return true;
}

function validateEmail() {
  if (checkIfEmpty(email)) return;
  if (!isEmail(email)) return;
  return true;
}

function validatePassword() {
  if (checkIfEmpty(password)) return;
  if (!isPasswordLength(password, 10)) return;
  return true;
}

// ***************************************************
// Util functions

function isEmail(email) {
  var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  if (email.value.match(re)) {
    setValid(email);
    return true;
  } else {
    setInvalid(email, "Invalid Email address");
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

function isAadhar(aadharNumber) {
  if (!isNaN(aadharNumber.value) && aadharNumber.value.length == 12) {
    setValid(aadharNumber);
    return true;
  } else {
    setInvalid(aadharNumber, "Wrong Aadhar Card number");
    return false;
  }
}

// ****************************************************

function checkIfEmpty(field) {
  if (isEmpty(field.value.trim())) {
    // Set field invalid
    setInvalid(field, "*Mandotary");
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

function checkIfOnlyLetters(field) {
  if (/^[a-zA-Z ]+$/.test(field.value)) {
    setValid(field);
    return true;
  } else {
    setInvalid(field, "*Characters only");
  }
}
