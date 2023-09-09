// Input fields
const password1 = document.getElementById("pass1");
const password2 = document.getElementById("pass2");

// Form
const form = document.getElementById("pass-form");

// Validate form

form.addEventListener("submit", function(event) {
  if (validatePassword1() && validatePassword2()) {
    console.log("submit");
  } else {
    // Prevent default behaviour
    event.preventDefault();
  }
});

// ***************************************************
// Validators
function validatePassword1() {
  if (checkIfEmpty(password1)) return;
  if (!isPasswordLength(password1, 10)) return;
  return true;
}

function validatePassword2() {
  if (checkIfEmpty(password2)) return;
  if (!isPasswordLength(password2, 10)) return;
  if (!passwordSame(password1, password2)) return;
  return true;
}

//  ****************************************************
//   Util functions

function isPasswordLength(password, length) {
  if (password.value.length >= length) {
    setValid(password);
    return true;
  } else {
    setInvalid(password, "Password is too short");
    return false;
  }
}

function passwordSame(pass1, pass2) {
  if (pass1.value === pass2.value) {
    setValid(pass2);
    return true;
  } else {
    setInvalid(pass2, "Passwords don't match");
    return false;
  }
}

//   ***************************************************
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
  const formControl = field.parentElement;
  const small = formControl.querySelector("small");
  formControl.className = "form-control error";
  small.innerText = message;
}

function setValid(field) {
  const formControl = field.parentElement;
  formControl.className = "form-control success";
}
