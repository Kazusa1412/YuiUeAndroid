package com.elouyi.yuiue.util

import com.elouyi.yuiue.R



/**
 * @see R.string.accountFormError
 */
fun checkAccount(account: String) = account.length >= 3

/**
 * @see R.string.passwordFormError
 */
fun checkPassword(password: String) = password.length >= 3