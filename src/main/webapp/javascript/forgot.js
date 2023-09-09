const email = document.getElementById("email");

const form = document.getElementById("forgot-form");

form.addEventListener("submit", function(event) {
  if (validateEmail()) {
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

function validateEmail() {
  if (checkIfEmpty(email)) return;
  if (!isEmail(email)) return;
  return true;
}

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
