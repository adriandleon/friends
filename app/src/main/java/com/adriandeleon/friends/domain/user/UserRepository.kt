package com.adriandeleon.friends.domain.user

import com.adriandeleon.friends.domain.exceptions.BackendException
import com.adriandeleon.friends.domain.exceptions.ConnectionUnavailableException
import com.adriandeleon.friends.domain.exceptions.DuplicateAccountException
import com.adriandeleon.friends.signup.state.SignUpState

class UserRepository(
    private val userCatalog: UserCatalog,
    private val userDataStore: UserDataStore
) {

    suspend fun signUp(
        email: String,
        password: String,
        about: String
    ): SignUpState {
        return try {
            val user = userCatalog.createUser(email, password, about)
            userDataStore.storeLoggedInUserId(user.id)
            SignUpState.SignedUp(user)
        } catch (duplicateAccount: DuplicateAccountException) {
            SignUpState.DuplicateAccount
        } catch (backendException: BackendException) {
            SignUpState.BackendError
        } catch (offlineException: ConnectionUnavailableException) {
            SignUpState.Offline
        }
    }
}