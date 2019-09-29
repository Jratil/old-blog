import React, { useState, useEffect } from 'react'
import { router } from 'umi'
import { Dispatch } from 'redux'
import { Form, Input, Button, Checkbox, message } from 'antd'
import { connect } from 'dva'
import { FormComponentProps } from 'antd/lib/form'
import { MIcon } from '@/components'

import styles from './index.less'

interface MProps extends FormComponentProps {
    dispatch: Dispatch
    loading: boolean
}

const FormItem = Form.Item

const rules = { account: [{ required: true, message: '请输入账号' }], password: [{ required: true, message: '请输入密码' }] }

const Login: React.FC<MProps> = props => {
    const { dispatch, form, loading } = props
    const { getFieldDecorator, setFieldsValue } = form
    const [account, setAccount] = useState<string>('')
    const [password, setPassword] = useState<string>('')

    useEffect(() => {
        const localAccount = window.localStorage.getItem('account') || ''
        const localPassword = window.localStorage.getItem('password') || ''
        if (localAccount) {
            setFieldsValue({ account: localAccount })
            setAccount(localAccount)
        }
        if (localPassword) {
            setFieldsValue({ password: localPassword })
            setPassword(localPassword)
        }
    }, [])

    const handleSubmit = evt => {
        evt.preventDefault()
        form.validateFields((err, payload) => {
            const { remember, account, password } = payload
            if (!err) {
                //  记住当前输入
                if (remember) {
                    window.localStorage.setItem('account', account)
                    window.localStorage.setItem('password', password)
                } else {
                    window.localStorage.setItem('account', '')
                    window.localStorage.setItem('password', '')
                }
                //  调用登录接口
                dispatch({
                    type: 'login/login',
                    payload: { account, password },
                    callback: ({ code, msg }) => {
                        if (code === 0) {
                            router.push('/')
                            //  重载一次，防止账号切换带来的问题
                            window.location.reload()
                        } else {
                            message.error(msg)
                        }
                    }
                })
            }
        })
    }

    const handelRegister = evt => {
        evt.preventDefault()
        router.push('/register')
    }

    const handleForget = evt => {
        evt.preventDefault()
        router.push('/forget')
    }

    return (
        <Form className={styles.formWrapper} onSubmit={handleSubmit}>
            <FormItem>
                {getFieldDecorator('account', {
                    initialValue: account,
                    rules: rules.account,
                    validateTrigger: 'onBlur'
                })(<Input prefix={<MIcon type="user" />} placeholder="Account"></Input>)}
            </FormItem>
            <FormItem>
                {getFieldDecorator('password', {
                    initialValue: password,
                    rules: rules.password,
                    validateTrigger: 'onBlur'
                })(<Input type="password" prefix={<MIcon type="lock" />} placeholder="Password"></Input>)}
            </FormItem>
            <FormItem>
                <div className={styles.btnWrapper}>
                    {getFieldDecorator('remember', {
                        valuePropName: 'checked',
                        initialValue: true
                    })(<Checkbox>记住密码</Checkbox>)}
                    <Button type="link" size="small" onClick={handleForget}>
                        找回密码
                    </Button>
                </div>
            </FormItem>

            <Button type="primary" onClick={handleSubmit} block loading={loading}>
                登录
            </Button>

            <Button type="link" onClick={handelRegister} block>
                没有账号？点我注册
            </Button>
        </Form>
    )
}

export default Form.create()(
    connect(({ loading }) => ({
        loading: loading.effects['login/login']
    }))(Login)
)
