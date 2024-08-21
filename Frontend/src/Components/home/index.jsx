import { useEffect, useState } from "react";
import { apiService } from "../Services";
import { toast } from "react-toastify";
import"./Home.css";
import { useNavigate } from "react-router-dom";

function Home() {
  const [countries, setCountries] = useState([]);
  const navigate = useNavigate();

  let pos = 1;

  useEffect(() => {
    async function loadCountries() {
      await apiService
        .getCountries()
        .then((response) => {
          setCountries(response.data);
          console.log("response: ", response.data);
        })
        .catch((error) => {
          console.log("error: ", error);
          toast(error.response.message);
        });
    }
    loadCountries();
  }, []);

  function Logout() {
    localStorage.clear("authToken");
    navigate("/Login");
  }

  return (
    <div className="mainContainer">
      <div className="searchContainer">
        <div className="logoutDiv">
          <img
            src="src\assets\logout-image.jpeg"
            className="imgLogout"
            onClick={() => {
              Logout();
            }}
            alt="logout"
          />
        </div>
        <div className="olympicRingsDiv">
          <img
            src="src\assets\olympic-rings-2.jpg"
            className="imgOlympicRing"
            alt="Olympic Rings"
          />
        </div>
        <div className="titlePageDiv">
          <h1 className="titlePage">Olympic Medal Monitor</h1>
        </div>
      </div>
      <div className="countriesContainer">
        <table className="tableStyle">
          <thead className="theadStyle">
            <tr>
              <th>Pos</th>
              <th>País</th>
              <th>Ouro</th>
              <th>Prata</th>
              <th>Bronze</th>
              <th>Total</th>
              <th>Seguindo</th>
            </tr>
          </thead>
          <tbody className="tbodyStyle">
            {countries.map((item) => {
              return (
                <tr
                  key={item.country.id}
                  className=""
                  onClick={() => {
                    navigate(`/CountryCard/${item.country.id}`);
                  }}
                >
                  <td>{pos++}</td>
                  <td>
                    <img
                      decoding="async"
                      src="https://th.bing.com/th/id/OIP.RgwzDihAMttqCx8f4GGTVAHaFL?rs=1&pid=ImgDetMain"
                      alt="imagem do card 1 html e css"
                      className="imgFlag"
                    />
                  </td>
                  <td>{0}</td>
                  <td>{0}</td>
                  <td>{0}</td>
                  <td>{0}</td>
                  <td>Não</td>
                </tr>
              );
            })}
          </tbody>
        </table>
      </div>
    </div>
  );
}
export default Home;
