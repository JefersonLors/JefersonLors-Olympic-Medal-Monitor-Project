import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import { apiService } from '../Services';
import './register.css';

function Register() {
    const [login, setLogin] = useState('');
    const [password, setPassword] = useState('');
    const [name, setName] = useState('');
    const [isloading, setIsLoading] = useState(false);
    const [hasFocus, setHasFocus] = useState(false);
    const navigate = useNavigate();

    localStorage.clear();

    async function confirmRegister() {
        setIsLoading(true);
        if (validateCredencials()) {
            await apiService
                .register({ login: login, password: password, rolesId: [2] })
                .then(async (responseA) => {
                    await apiService
                        .postUser({ name: name, email: login })
                        .then((response) => {
                            toast.success(responseA.data);
                            navigate('/Login');
                        })
                        .catch((error) => {
                            toast.error(error.response.data.message);
                            console.log('erro ao criar usuário', error);
                        });
                })
                .catch((error) => {
                    toast.error(error.response.data.message);
                    console.log('erro ao autenticar o usuário', error);
                });
        }
        setIsLoading(false);
    }

    function validateCredencials() {
        if (name.length < 1) {
            toast.error('O nome é obrigatório.');
            return false;
        }

        if (login.length < 1) {
            toast.error('O login é obrigatório.');
            return false;
        }

        if (password.length < 1) {
            toast.error('A senha é obrigatória.');
            return false;
        }
        return true;
    }

    function handleKeyDown(event) {
        if (event.key === 'Enter' && hasFocus) {
            const botao = document.getElementById('confirmRegisterButton');
            botao!.click();
        }
    }

    useEffect(()=>{
        function putFocus(){
            const input = document.getElementById("registerNameField");
            input?.focus();
        }
        putFocus();
    }, [])

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
                <h1>Register</h1>
                <form>
                    <div className="txt_field">
                        <input
                            id="registerNameField"
                            type="text"
                            name="registerNameField"
                            value={name}
                            onKeyDown={() => {
                                handleKeyDown(event);
                            }}
                            onFocus={() => setHasFocus(true)}
                            onBlur={() => setHasFocus(false)}
                            onChange={(e) => {
                                setName(e.target.value);
                            }}
                            required
                        />
                        <span></span>
                        <label>Nome</label>
                    </div>
                    <div className="txt_field">
                        <input
                            type="text"
                            name="registerLoginField"
                            value={login}
                            onKeyDown={() => {
                                handleKeyDown(event);
                            }}
                            onFocus={() => setHasFocus(true)}
                            onBlur={() => setHasFocus(false)}
                            onChange={(e) => {
                                setLogin(e.target.value);
                            }}
                            required
                        />
                        <span></span>
                        <label>Email</label>
                    </div>

                    <div className="txt_field">
                        <input
                            type="password"
                            name="registerPasswordField"
                            value={password}
                            onKeyDown={() => {
                                handleKeyDown(event);
                            }}
                            onFocus={() => setHasFocus(true)}
                            onBlur={() => setHasFocus(false)}
                            onChange={(e) => {
                                setPassword(e.target.value);
                            }}
                            required
                        />
                        <span></span>
                        <label>Senha</label>
                    </div>
                    <button
                        id="confirmRegisterButton"
                        name="confirmRegisterButton"
                        type="button"
                        value="confirmRegister"
                        className="buttonRegister"
                        onClick={() => {
                            confirmRegister();
                        }}
                    >
                        Confirmar
                    </button>
                    <div className="signup_link">
                        Já tem cadastro ? <Link to="/Login">Entrar</Link>
                    </div>
                </form>
            </div>
        </div>
    );
}
export default Register;
