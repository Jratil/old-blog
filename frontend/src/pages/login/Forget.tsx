import React, { useState, useEffect } from 'react'
import { router } from 'umi'
import { Dispatch } from 'redux'
import { Form, Input, Button, message } from 'antd'
import { connect } from 'dva'
import { FormComponentProps } from 'antd/lib/form'
import { MIcon } from '@/components'
import styles from './index.less'

const FormItem = Form.Item

interface MProps extends FormComponentProps {
    dispatch: Dispatch
    loading: boolean
}

const rules = {
    account: [{ required: true, message: '请输入账号' }],
    password: [{ required: true, message: '请输入密码' }],
    verifyCode: [{ required: true, message: '请输入验证码' }]
}

const Forget: React.FC<MProps> = ({ dispatch, form, loading }) => {
    const { getFieldDecorator, setFieldsValue, getFieldValue } = form
    const [sendStatus, setSendStatus] = useState<boolean>(false)
    const [sendText, setSendText] = useState<string>('发送验证码')

    let timer: any = null

    useEffect(() => () => clearInterval(timer), [])

    const handleSubmit = evt => {
        evt.preventDefault()
        form.validateFields((err, payload) => {
            const { account, password } = payload
            if (!err) {
                //  调用忘记密码接口
                dispatch({
                    type: 'login/forget',
                    payload,
                    callback: ({ code, msg }) => {
                        if (code === 0) {
                            message.success('密码重置成功，正在跳转到登录页面...')
                            window.localStorage.setItem('account', account)
                            window.localStorage.setItem('password', password)
                            router.push('/login')
                        } else {
                            message.error(msg)
                        }
                    }
                })
            }
        })
    }

    const handleSend = evt => {
        evt.preventDefault()
        const account = getFieldValue('account')
        if (account === '') {
            message.error('请先输入您的账号！')
        } else {
            dispatch({
                type: 'login/getVerifyCode',
                payload: { account },
                callback: ({ code, msg }) => {
                    if (code === 0) {
                        let countDown = 60
                        setSendStatus(true)
                        timer = setInterval(() => {
                            if (countDown === 0) {
                                clearInterval(timer)
                                setSendStatus(false)
                                setSendText('再次发送')
                            } else {
                                setSendText(`${countDown--}S`)
                            }
                        }, 1000)
                    } else {
                        message.error(msg)
                    }
                }
            })
        }
    }

    return (
        <Form className={styles.formWrapper}>
            <FormItem>
                {getFieldDecorator('account', {
                    rules: rules.account,
                    validateTrigger: 'onBlur'
                })(<Input prefix={<MIcon type="user" />} placeholder="Account"></Input>)}
            </FormItem>
            <FormItem>
                {getFieldDecorator('password', {
                    rules: rules.password,
                    validateTrigger: 'onBlur'
                })(<Input type="password" prefix={<MIcon type="lock" />} placeholder="Password"></Input>)}
            </FormItem>
            <FormItem>
                <div className={styles.sendWrapper}>
                    {getFieldDecorator('verifyCode', {
                        rules: rules.verifyCode,
                        validateTrigger: 'onBlur'
                    })(<Input prefix={<MIcon type="code" />} placeholder="verifyCode"></Input>)}
                    <Button onClick={handleSend} className={styles.sendBtn} disabled={sendStatus}>
                        {sendText}
                    </Button>
                </div>
            </FormItem>

            <Button type="primary" onClick={handleSubmit} block loading={loading}>
                找回密码
            </Button>
        </Form>
    )
}

export default connect(({ loading }) => ({
    loading: loading.effects['login/forget']
}))(Form.create()(Forget))
