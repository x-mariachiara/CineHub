// eagerly import theme styles so as we can override them
import '@vaadin/vaadin-lumo-styles/all-imports';

const $_documentContainer = document.createElement('template');

$_documentContainer.innerHTML = `
<custom-style>
  <style>
    html {
      --lumo-primary-color: #BF0404;
      --lumo-error-color: #594842;
      --lumo-success-color: #F2C777;
      --lumo-primary-text-color: #BF0404;
      --lumo-error-text-color: #594842;
      --lumo-success-text-color: #F2C777;
      --lumo-border-radius: calc(var(--lumo-size-m) / 2);
      --lumo-body-text-color: hsla(279, 0%, 0%, 0.94);
    }
  </style>
</custom-style>


`;

document.head.appendChild($_documentContainer.content);
