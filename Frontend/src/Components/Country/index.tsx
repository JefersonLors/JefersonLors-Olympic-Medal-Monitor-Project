import React from "react";

function CountryCard(key, countryName) {
  return (
    <section className="secao4" id="sobre">
      <div className="secao4-div">
        <div className="secao4-div-card">
          <img
            decoding="async"
            src="https://th.bing.com/th/id/OIP.RgwzDihAMttqCx8f4GGTVAHaFL?rs=1&pid=ImgDetMain"
            alt="imagem do card 1 html e css"
          />
          <h3>{key}</h3>
          <p>{countryName}</p>
        </div>
      </div>
    </section>
  );
}export default CountryCard;
