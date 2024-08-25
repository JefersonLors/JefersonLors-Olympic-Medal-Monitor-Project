import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import { apiService } from '../Services';
import './login.css';

function Login() {
    const [login, setLogin] = useState('');
    const [password, setPassword] = useState('');
    const [isloading, setIsLoading] = useState(false);
    const navigate = useNavigate();

    localStorage.clear();

    async function confirmLogin() {
        setIsLoading(true);
        if (validateCredencials()) {
            await apiService
                .login({ login: login, password: password })
                .then(async (responseA) => {
                    localStorage.setItem('authToken', responseA.data);
                    const token = responseA.data;
                    await apiService
                        .getUserByEmail(login)
                        .then(async (responseB) => {
                            localStorage.setItem('user', JSON.stringify(responseB.data));
                            await apiService
                                .getUserRoles({ value: "Bearer " + token })
                                .then((responseC) => {
                                    localStorage.setItem('userRoles', responseC.data);
                                    navigate('/Home');
                                })
                                .catch((error) => {
                                    toast.error(error.response.data.message);
                                    console.log('error durante a recuperação das roles do usuário.', error);
                                });
                        })
                        .catch((error) => {
                            toast.error(error.response.data.message);
                            console.log('Erro durante a recuperação do usuário por email', error);
                        });
                })
                .catch((error) => {
                    toast.error(error.response.data.message);
                    console.log('Erro durante validar credenciais do usuário', error);
                });
        }
        setIsLoading(false);
    }
    function validateCredencials() {
        if (login == null || login.length < 1) {
            toast.error('O login é obrigatório.');
            return false;
        }

        if (password == null || password.length < 1) {
            toast.error('A senha é obrigatória.');
            return false;
        }
        return true;
    }

    if (isloading) {
        return (
            <div className="loading">
                <div className="loading-circle"></div>
            </div>
        );
    }
    return (
        <div className="container">
            <div className="center">
                <h1>Entrar</h1>
                <form>
                    <div className="txt_field">
                        <input
                            id="emailField"
                            type="text"
                            name="emailField"
                            value={login}
                            onChange={(e) => {
                                setLogin(e.target.value);
                            }}
                            required
                        />
                        <span></span>
                        <label>E-mail</label>
                    </div>

                    <div className="txt_field">
                        <input
                            id="passwordField"
                            type="password"
                            name="passwordField"
                            value={password}
                            onChange={(e) => {
                                setPassword(e.target.value);
                            }}
                            required
                        />
                        <span></span>
                        <label>Senha</label>
                    </div>
                    <button
                        type="button"
                        id="confirmButton"
                        name="confirmButton"
                        className="buttonLogin"
                        onClick={() => {
                            confirmLogin();
                        }}
                    >
                        Confirmar
                    </button>
                    <div className="signup_link">
                        Não tem cadastro ? <Link to="/Register">Cadastrar</Link>
                    </div>
                </form>
            </div>
        </div>
    );
}
export default Login;
